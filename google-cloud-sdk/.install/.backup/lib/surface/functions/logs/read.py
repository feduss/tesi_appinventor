# -*- coding: utf-8 -*- #
# Copyright 2015 Google LLC. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""Displays log entries produced by Google Cloud Functions."""

from __future__ import absolute_import
from __future__ import division
from __future__ import unicode_literals

from googlecloudsdk.api_lib.functions import util
from googlecloudsdk.api_lib.logging import common as logging_common
from googlecloudsdk.api_lib.logging import util as logging_util
from googlecloudsdk.calliope import arg_parsers
from googlecloudsdk.calliope import base
from googlecloudsdk.command_lib.functions import flags
from googlecloudsdk.core import properties
import six


class GetLogs(base.ListCommand):
  """Display log entries produced by Google Cloud Functions."""

  @staticmethod
  def Args(parser):
    """Register flags for this command."""
    flags.AddRegionFlag(
        parser,
        help_text='Only show logs generated by functions in the region.',
    )
    base.LIMIT_FLAG.RemoveFromParser(parser)
    parser.add_argument(
        'name', nargs='?',
        help=('Name of the function which logs are to be displayed. If no name '
              'is specified, logs from all functions are displayed.'))
    parser.add_argument(
        '--execution-id',
        help=('Execution ID for which logs are to be displayed.'))
    parser.add_argument(
        '--start-time', required=False, type=arg_parsers.Datetime.Parse,
        help=('Return only log entries which timestamps are not earlier than '
              'the specified time. If *--start-time* is specified, the command '
              'returns *--limit* earliest log entries which appeared after '
              '*--start-time*. See $ gcloud topic datetimes for information '
              'on time formats.'))
    parser.add_argument(
        '--end-time', required=False, type=arg_parsers.Datetime.Parse,
        help=('Return only log entries which timestamps are not later than '
              'the specified time. If *--end-time* is specified but '
              '*--start-time* is not, the command returns *--limit* latest '
              'log entries which appeared before --end-time. See '
              '$ gcloud topic datetimes for information on time formats.'))
    parser.add_argument(
        '--limit', required=False, type=arg_parsers.BoundedInt(1, 1000),
        default=20,
        help=('Number of log entries to be fetched; must not be greater than '
              '1000.'))
    flags.AddMinLogLevelFlag(parser)
    parser.display_info.AddCacheUpdater(None)

  @util.CatchHTTPErrorRaiseHTTPException
  def Run(self, args):
    """This is what gets called when the user runs this command.

    Args:
      args: an argparse namespace. All the arguments that were provided to this
        command invocation.

    Returns:
      A generator of objects representing log entries.
    """
    if not args.IsSpecified('format'):
      args.format = self._Format(args)

    return self._Run(args)

  def _Run(self, args):
    region = properties.VALUES.functions.region.Get()
    log_filter = ['resource.type="cloud_function"',
                  'resource.labels.region="%s"' % region,
                  'logName:"cloud-functions"']

    if args.name:
      log_filter.append('resource.labels.function_name="%s"' % args.name)
    if args.execution_id:
      log_filter.append('labels.execution_id="%s"' % args.execution_id)
    if args.min_log_level:
      log_filter.append('severity>=%s' % args.min_log_level.upper())
    if args.start_time:
      order = 'ASC'
      log_filter.append(
          'timestamp>="%s"' % logging_util.FormatTimestamp(args.start_time))
    else:
      order = 'DESC'
    if args.end_time:
      log_filter.append(
          'timestamp<="%s"' % logging_util.FormatTimestamp(args.end_time))
    log_filter = ' '.join(log_filter)

    entries = logging_common.FetchLogs(
        log_filter, order_by=order, limit=args.limit)

    if order == 'DESC':
      entries = reversed(list(entries))  # Force generator expansion with list.

    for entry in entries:
      message = entry.textPayload
      if entry.jsonPayload:
        props = [prop.value for prop in entry.jsonPayload.additionalProperties
                 if prop.key == 'message']
        if len(props) == 1 and hasattr(props[0], 'string_value'):
          message = props[0].string_value
      row = {'log': message}
      if entry.severity:
        severity = six.text_type(entry.severity)
        if severity in flags.SEVERITIES:
          # Use short form (first letter) for expected severities.
          row['level'] = severity[0]
        else:
          # Print full form of unexpected severities.
          row['level'] = severity
      if entry.resource and entry.resource.labels:
        for label in entry.resource.labels.additionalProperties:
          if label.key == 'function_name':
            row['name'] = label.value
      if entry.labels:
        for label in entry.labels.additionalProperties:
          if label.key == 'execution_id':
            row['execution_id'] = label.value
      if entry.timestamp:
        row['time_utc'] = util.FormatTimestamp(entry.timestamp)
      yield row

  def _Format(self, args):
    return 'table(level,name,execution_id,time_utc,log)'
