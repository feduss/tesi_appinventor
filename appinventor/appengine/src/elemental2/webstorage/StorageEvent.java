package elemental2.webstorage;

import elemental2.dom.Event;
import elemental2.dom.EventInit;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class StorageEvent extends Event {
  public String key;
  public String newValue;
  public String oldValue;
  public Storage storageArea;
  public String url;

  public StorageEvent() {
    // This call is only here for java compilation purpose.
    super((String) null, (EventInit) null);
  }

  public native void initStorageEvent(
      String typeArg,
      boolean canBubbleArg,
      boolean cancelableArg,
      String keyArg,
      String oldValueArg,
      String newValueArg,
      String urlArg,
      Storage storageAreaArg);
}
