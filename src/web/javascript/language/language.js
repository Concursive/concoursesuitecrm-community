// language functions
function label(param, defaultValue) {
  if (FrameworkI18N) {
    var value = FrameworkI18N.message[param];
    if (!value) {
      return defaultValue;
    }
  }
  return value;
}
