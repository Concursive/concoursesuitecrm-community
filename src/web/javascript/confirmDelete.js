/**
 * Displays a confirmation to the user
 * @arg1 = URL to forward to if confirmation returns true
 */
function confirmDelete(url) {
  if (confirm(label("are.you.sure", "Are you sure?"))) {
    window.location = url;
  }
}

function confirmForward(url) {
  if (confirm(label("are.you.sure", "Are you sure?"))) {
    window.location = url;
  }
}

function confirmAction(msg) {
  if (confirm(msg)) {
    return true;
  } else {
    return false;
  }
}

function confirmSubmit(theForm) {
  if (confirm(label("are.you.sure", "Are you sure?"))) {
    theForm.submit();
  } else {
    return false;
  }
}
