package elemental2.webstorage;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface WindowLocalStorage {
  @JsProperty
  Storage getLocalStorage();

  @JsProperty
  void setLocalStorage(Storage localStorage);
}
