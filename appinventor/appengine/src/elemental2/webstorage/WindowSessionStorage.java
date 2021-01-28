package elemental2.webstorage;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface WindowSessionStorage {
  @JsProperty
  Storage getSessionStorage();

  @JsProperty
  void setSessionStorage(Storage sessionStorage);
}
