function textCounter(field, countfield, maxlimit) {
  if (field.value.length > maxlimit) {
    field.value = field.value.substring(0, maxlimit);
    if (field.createTextRange) {
      var range = field.createTextRange();
      range.moveStart('character', field.value.length);
      range.collapse(false);
      range.select();
    }
    alert('Your message may not exceed ' + maxlimit + ' characters in length.');
  } else {
    countfield.value = maxlimit - field.value.length;
  }
}
