# Text input with scene2D UI #

This libGDX test uses text input capabilities of [scene2d UI](scene2dUI.md) (ll. 109ff):

http://code.google.com/p/libgdx/source/browse/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/UITest.java



```java

textfield.setTextFieldListener(new TextFieldListener() {
@Override
public void keyTyped (TextField textField, char key) {
if (key == '\n') textField.getOnscreenKeyboard().show(false);
}
});```