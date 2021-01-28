package elemental2.webstorage;

import elemental2.dom.Window;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@JsType(isNative = true, name = "Window", namespace = JsPackage.GLOBAL)
public class WebStorageWindow extends Window {
  @JsOverlay
  public static WebStorageWindow of(Window o) {
    return Js.cast(o);
  }

  public Storage localStorage;
  public Storage sessionStorage;
}
