// language functions
function label(param, defaultValue) {
  try {
    if (FrameworkI18N) {
      var value = FrameworkI18N.message[param];
      if (!value) {
        return defaultValue;
      }
    }
    return value;
  } catch (ex) {
  }
  return defaultValue;
}
