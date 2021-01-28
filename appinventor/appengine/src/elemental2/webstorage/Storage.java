package elemental2.webstorage;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public interface Storage {
  void clear();

  String getItem(String key);

  @JsProperty
  int getLength();

  String key(int index);

  void removeItem(String key);

  void setItem(String key, String data);
}
