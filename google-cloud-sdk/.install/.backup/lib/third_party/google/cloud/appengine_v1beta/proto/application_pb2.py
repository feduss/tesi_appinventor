# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: google/cloud/appengine_v1beta/proto/application.proto

from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from google.protobuf import duration_pb2 as google_dot_protobuf_dot_duration__pb2
from google.api import annotations_pb2 as google_dot_api_dot_annotations__pb2


DESCRIPTOR = _descriptor.FileDescriptor(
  name='google/cloud/appengine_v1beta/proto/application.proto',
  package='google.appengine.v1beta',
  syntax='proto3',
  serialized_options=b'\n\033com.google.appengine.v1betaB\020ApplicationProtoP\001Z@google.golang.org/genproto/googleapis/appengine/v1beta;appengine',
  create_key=_descriptor._internal_create_key,
  serialized_pb=b'\n5google/cloud/appengine_v1beta/proto/application.proto\x12\x17google.appengine.v1beta\x1a\x1egoogle/protobuf/duration.proto\x1a\x1cgoogle/api/annotations.proto\"\x84\x08\n\x0b\x41pplication\x12\x0c\n\x04name\x18\x01 \x01(\t\x12\n\n\x02id\x18\x02 \x01(\t\x12@\n\x0e\x64ispatch_rules\x18\x03 \x03(\x0b\x32(.google.appengine.v1beta.UrlDispatchRule\x12\x13\n\x0b\x61uth_domain\x18\x06 \x01(\t\x12\x13\n\x0blocation_id\x18\x07 \x01(\t\x12\x13\n\x0b\x63ode_bucket\x18\x08 \x01(\t\x12<\n\x19\x64\x65\x66\x61ult_cookie_expiration\x18\t \x01(\x0b\x32\x19.google.protobuf.Duration\x12J\n\x0eserving_status\x18\n \x01(\x0e\x32\x32.google.appengine.v1beta.Application.ServingStatus\x12\x18\n\x10\x64\x65\x66\x61ult_hostname\x18\x0b \x01(\t\x12\x16\n\x0e\x64\x65\x66\x61ult_bucket\x18\x0c \x01(\t\x12\x44\n\x03iap\x18\x0e \x01(\x0b\x32\x37.google.appengine.v1beta.Application.IdentityAwareProxy\x12\x12\n\ngcr_domain\x18\x10 \x01(\t\x12H\n\rdatabase_type\x18\x11 \x01(\x0e\x32\x31.google.appengine.v1beta.Application.DatabaseType\x12N\n\x10\x66\x65\x61ture_settings\x18\x12 \x01(\x0b\x32\x34.google.appengine.v1beta.Application.FeatureSettings\x1a\x82\x01\n\x12IdentityAwareProxy\x12\x0f\n\x07\x65nabled\x18\x01 \x01(\x08\x12\x18\n\x10oauth2_client_id\x18\x02 \x01(\t\x12\x1c\n\x14oauth2_client_secret\x18\x03 \x01(\t\x12#\n\x1boauth2_client_secret_sha256\x18\x04 \x01(\t\x1aR\n\x0f\x46\x65\x61tureSettings\x12\x1b\n\x13split_health_checks\x18\x01 \x01(\x08\x12\"\n\x1ause_container_optimized_os\x18\x02 \x01(\x08\"U\n\rServingStatus\x12\x0f\n\x0bUNSPECIFIED\x10\x00\x12\x0b\n\x07SERVING\x10\x01\x12\x11\n\rUSER_DISABLED\x10\x02\x12\x13\n\x0fSYSTEM_DISABLED\x10\x03\"z\n\x0c\x44\x61tabaseType\x12\x1d\n\x19\x44\x41TABASE_TYPE_UNSPECIFIED\x10\x00\x12\x13\n\x0f\x43LOUD_DATASTORE\x10\x01\x12\x13\n\x0f\x43LOUD_FIRESTORE\x10\x02\x12!\n\x1d\x43LOUD_DATASTORE_COMPATIBILITY\x10\x03\"@\n\x0fUrlDispatchRule\x12\x0e\n\x06\x64omain\x18\x01 \x01(\t\x12\x0c\n\x04path\x18\x02 \x01(\t\x12\x0f\n\x07service\x18\x03 \x01(\tBs\n\x1b\x63om.google.appengine.v1betaB\x10\x41pplicationProtoP\x01Z@google.golang.org/genproto/googleapis/appengine/v1beta;appengineb\x06proto3'
  ,
  dependencies=[google_dot_protobuf_dot_duration__pb2.DESCRIPTOR,google_dot_api_dot_annotations__pb2.DESCRIPTOR,])



_APPLICATION_SERVINGSTATUS = _descriptor.EnumDescriptor(
  name='ServingStatus',
  full_name='google.appengine.v1beta.Application.ServingStatus',
  filename=None,
  file=DESCRIPTOR,
  create_key=_descriptor._internal_create_key,
  values=[
    _descriptor.EnumValueDescriptor(
      name='UNSPECIFIED', index=0, number=0,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='SERVING', index=1, number=1,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='USER_DISABLED', index=2, number=2,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='SYSTEM_DISABLED', index=3, number=3,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
  ],
  containing_type=None,
  serialized_options=None,
  serialized_start=964,
  serialized_end=1049,
)
_sym_db.RegisterEnumDescriptor(_APPLICATION_SERVINGSTATUS)

_APPLICATION_DATABASETYPE = _descriptor.EnumDescriptor(
  name='DatabaseType',
  full_name='google.appengine.v1beta.Application.DatabaseType',
  filename=None,
  file=DESCRIPTOR,
  create_key=_descriptor._internal_create_key,
  values=[
    _descriptor.EnumValueDescriptor(
      name='DATABASE_TYPE_UNSPECIFIED', index=0, number=0,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='CLOUD_DATASTORE', index=1, number=1,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='CLOUD_FIRESTORE', index=2, number=2,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
    _descriptor.EnumValueDescriptor(
      name='CLOUD_DATASTORE_COMPATIBILITY', index=3, number=3,
      serialized_options=None,
      type=None,
      create_key=_descriptor._internal_create_key),
  ],
  containing_type=None,
  serialized_options=None,
  serialized_start=1051,
  serialized_end=1173,
)
_sym_db.RegisterEnumDescriptor(_APPLICATION_DATABASETYPE)


_APPLICATION_IDENTITYAWAREPROXY = _descriptor.Descriptor(
  name='IdentityAwareProxy',
  full_name='google.appengine.v1beta.Application.IdentityAwareProxy',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='enabled', full_name='google.appengine.v1beta.Application.IdentityAwareProxy.enabled', index=0,
      number=1, type=8, cpp_type=7, label=1,
      has_default_value=False, default_value=False,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='oauth2_client_id', full_name='google.appengine.v1beta.Application.IdentityAwareProxy.oauth2_client_id', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='oauth2_client_secret', full_name='google.appengine.v1beta.Application.IdentityAwareProxy.oauth2_client_secret', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='oauth2_client_secret_sha256', full_name='google.appengine.v1beta.Application.IdentityAwareProxy.oauth2_client_secret_sha256', index=3,
      number=4, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=748,
  serialized_end=878,
)

_APPLICATION_FEATURESETTINGS = _descriptor.Descriptor(
  name='FeatureSettings',
  full_name='google.appengine.v1beta.Application.FeatureSettings',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='split_health_checks', full_name='google.appengine.v1beta.Application.FeatureSettings.split_health_checks', index=0,
      number=1, type=8, cpp_type=7, label=1,
      has_default_value=False, default_value=False,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='use_container_optimized_os', full_name='google.appengine.v1beta.Application.FeatureSettings.use_container_optimized_os', index=1,
      number=2, type=8, cpp_type=7, label=1,
      has_default_value=False, default_value=False,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=880,
  serialized_end=962,
)

_APPLICATION = _descriptor.Descriptor(
  name='Application',
  full_name='google.appengine.v1beta.Application',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='name', full_name='google.appengine.v1beta.Application.name', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='id', full_name='google.appengine.v1beta.Application.id', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='dispatch_rules', full_name='google.appengine.v1beta.Application.dispatch_rules', index=2,
      number=3, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='auth_domain', full_name='google.appengine.v1beta.Application.auth_domain', index=3,
      number=6, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='location_id', full_name='google.appengine.v1beta.Application.location_id', index=4,
      number=7, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='code_bucket', full_name='google.appengine.v1beta.Application.code_bucket', index=5,
      number=8, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='default_cookie_expiration', full_name='google.appengine.v1beta.Application.default_cookie_expiration', index=6,
      number=9, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='serving_status', full_name='google.appengine.v1beta.Application.serving_status', index=7,
      number=10, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='default_hostname', full_name='google.appengine.v1beta.Application.default_hostname', index=8,
      number=11, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='default_bucket', full_name='google.appengine.v1beta.Application.default_bucket', index=9,
      number=12, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='iap', full_name='google.appengine.v1beta.Application.iap', index=10,
      number=14, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='gcr_domain', full_name='google.appengine.v1beta.Application.gcr_domain', index=11,
      number=16, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='database_type', full_name='google.appengine.v1beta.Application.database_type', index=12,
      number=17, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='feature_settings', full_name='google.appengine.v1beta.Application.feature_settings', index=13,
      number=18, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[_APPLICATION_IDENTITYAWAREPROXY, _APPLICATION_FEATURESETTINGS, ],
  enum_types=[
    _APPLICATION_SERVINGSTATUS,
    _APPLICATION_DATABASETYPE,
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=145,
  serialized_end=1173,
)


_URLDISPATCHRULE = _descriptor.Descriptor(
  name='UrlDispatchRule',
  full_name='google.appengine.v1beta.UrlDispatchRule',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='domain', full_name='google.appengine.v1beta.UrlDispatchRule.domain', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='path', full_name='google.appengine.v1beta.UrlDispatchRule.path', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='service', full_name='google.appengine.v1beta.UrlDispatchRule.service', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=1175,
  serialized_end=1239,
)

_APPLICATION_IDENTITYAWAREPROXY.containing_type = _APPLICATION
_APPLICATION_FEATURESETTINGS.containing_type = _APPLICATION
_APPLICATION.fields_by_name['dispatch_rules'].message_type = _URLDISPATCHRULE
_APPLICATION.fields_by_name['default_cookie_expiration'].message_type = google_dot_protobuf_dot_duration__pb2._DURATION
_APPLICATION.fields_by_name['serving_status'].enum_type = _APPLICATION_SERVINGSTATUS
_APPLICATION.fields_by_name['iap'].message_type = _APPLICATION_IDENTITYAWAREPROXY
_APPLICATION.fields_by_name['database_type'].enum_type = _APPLICATION_DATABASETYPE
_APPLICATION.fields_by_name['feature_settings'].message_type = _APPLICATION_FEATURESETTINGS
_APPLICATION_SERVINGSTATUS.containing_type = _APPLICATION
_APPLICATION_DATABASETYPE.containing_type = _APPLICATION
DESCRIPTOR.message_types_by_name['Application'] = _APPLICATION
DESCRIPTOR.message_types_by_name['UrlDispatchRule'] = _URLDISPATCHRULE
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

Application = _reflection.GeneratedProtocolMessageType('Application', (_message.Message,), {

  'IdentityAwareProxy' : _reflection.GeneratedProtocolMessageType('IdentityAwareProxy', (_message.Message,), {
    'DESCRIPTOR' : _APPLICATION_IDENTITYAWAREPROXY,
    '__module__' : 'google.cloud.appengine_v1beta.proto.application_pb2'
    ,
    '__doc__': """Identity-Aware Proxy
    
    Attributes:
        enabled:
            Whether the serving infrastructure will authenticate and
            authorize all incoming requests.  If true, the
            ``oauth2_client_id`` and ``oauth2_client_secret`` fields must
            be non-empty.
        oauth2_client_id:
            OAuth2 client ID to use for the authentication flow.
        oauth2_client_secret:
            OAuth2 client secret to use for the authentication flow.  For
            security reasons, this value cannot be retrieved via the API.
            Instead, the SHA-256 hash of the value is returned in the
            ``oauth2_client_secret_sha256`` field.  @InputOnly
        oauth2_client_secret_sha256:
            Hex-encoded SHA-256 hash of the client secret.  @OutputOnly
    """,
    # @@protoc_insertion_point(class_scope:google.appengine.v1beta.Application.IdentityAwareProxy)
    })
  ,

  'FeatureSettings' : _reflection.GeneratedProtocolMessageType('FeatureSettings', (_message.Message,), {
    'DESCRIPTOR' : _APPLICATION_FEATURESETTINGS,
    '__module__' : 'google.cloud.appengine_v1beta.proto.application_pb2'
    ,
    '__doc__': """The feature specific settings to be used in the application. These
    define behaviors that are user configurable.
    
    Attributes:
        split_health_checks:
            Boolean value indicating if split health checks should be used
            instead of the legacy health checks. At an app.yaml level,
            this means defaulting to ‘readiness_check’ and
            ‘liveness_check’ values instead of ‘health_check’ ones. Once
            the legacy ‘health_check’ behavior is deprecated, and this
            value is always true, this setting can be removed.
        use_container_optimized_os:
            If true, use `Container-Optimized OS
            <https://cloud.google.com/container-optimized-os/>`__ base
            image for VMs, rather than a base Debian image.
    """,
    # @@protoc_insertion_point(class_scope:google.appengine.v1beta.Application.FeatureSettings)
    })
  ,
  'DESCRIPTOR' : _APPLICATION,
  '__module__' : 'google.cloud.appengine_v1beta.proto.application_pb2'
  ,
  '__doc__': """An Application resource contains the top-level configuration of an App
  Engine application.
  
  Attributes:
      name:
          Full path to the Application resource in the API. Example:
          ``apps/myapp``.  @OutputOnly
      id:
          Identifier of the Application resource. This identifier is
          equivalent to the project ID of the Google Cloud Platform
          project where you want to deploy your application. Example:
          ``myapp``.
      dispatch_rules:
          HTTP path dispatch rules for requests to the application that
          do not explicitly target a service or version. Rules are
          order-dependent. Up to 20 dispatch rules can be supported.
      auth_domain:
          Google Apps authentication domain that controls which users
          can access this application.  Defaults to open access for any
          Google Account.
      location_id:
          Location from which this application runs. Application
          instances run out of the data centers in the specified
          location, which is also where all of the application’s end
          user content is stored.  Defaults to ``us-central``.  View the
          list of `supported locations
          <https://cloud.google.com/appengine/docs/locations>`__.
      code_bucket:
          Google Cloud Storage bucket that can be used for storing files
          associated with this application. This bucket is associated
          with the application and can be used by the gcloud deployment
          commands.  @OutputOnly
      default_cookie_expiration:
          Cookie expiration policy for this application.
      serving_status:
          Serving status of this application.
      default_hostname:
          Hostname used to reach this application, as resolved by App
          Engine.  @OutputOnly
      default_bucket:
          Google Cloud Storage bucket that can be used by this
          application to store content.  @OutputOnly
      gcr_domain:
          The Google Container Registry domain used for storing managed
          build docker images for this application.
      database_type:
          The type of the Cloud Firestore or Cloud Datastore database
          associated with this application.
      feature_settings:
          The feature specific settings to be used in the application.
  """,
  # @@protoc_insertion_point(class_scope:google.appengine.v1beta.Application)
  })
_sym_db.RegisterMessage(Application)
_sym_db.RegisterMessage(Application.IdentityAwareProxy)
_sym_db.RegisterMessage(Application.FeatureSettings)

UrlDispatchRule = _reflection.GeneratedProtocolMessageType('UrlDispatchRule', (_message.Message,), {
  'DESCRIPTOR' : _URLDISPATCHRULE,
  '__module__' : 'google.cloud.appengine_v1beta.proto.application_pb2'
  ,
  '__doc__': """Rules to match an HTTP request and dispatch that request to a service.
  
  Attributes:
      domain:
          Domain name to match against. The wildcard “``*``” is
          supported if specified before a period: “``*.``”.  Defaults to
          matching all domains: “``*``”.
      path:
          Pathname within the host. Must start with a “``/``”. A single
          “``*``” can be included at the end of the path.  The sum of
          the lengths of the domain and path may not exceed 100
          characters.
      service:
          Resource ID of a service in this application that should serve
          the matched request. The service must already exist. Example:
          ``default``.
  """,
  # @@protoc_insertion_point(class_scope:google.appengine.v1beta.UrlDispatchRule)
  })
_sym_db.RegisterMessage(UrlDispatchRule)


DESCRIPTOR._options = None
# @@protoc_insertion_point(module_scope)
