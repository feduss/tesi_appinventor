# -*- coding: utf-8 -*- #
# Copyright 2019 Google LLC. All Rights Reserved.
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
"""Shared code to create test matrices in Firebase Test Lab."""

from __future__ import absolute_import
from __future__ import division
from __future__ import unicode_literals

from googlecloudsdk.core import config
import six


def BuildClientInfo(messages, client_details, release_track):
  """Build the ClientInfo part of a TestMatrix message.

  Sets the client name to 'gcloud' and attaches common and user-provided client
  details to the ClientInfo message.

  Args:
    messages: Testing API messages generated by Apitools.
    client_details: Dictionary of user-provided client_details.
    release_track: Release track that the command is invoked from.

  Returns:
    ClientInfo message.
  """
  details_with_defaults = dict(client_details)
  details_with_defaults['Cloud SDK Version'] = config.CLOUD_SDK_VERSION
  details_with_defaults['Release Track'] = release_track
  client_info_details = []

  for key, value in six.iteritems(details_with_defaults):
    client_info_details.append(messages.ClientInfoDetail(key=key, value=value))

  # Sort by key to have a predictable order for testing
  client_info_details.sort(key=lambda d: d.key)

  return messages.ClientInfo(
      name='gcloud', clientInfoDetails=client_info_details)
