/**
 * Displays a confirmation to the user
 * @arg1 = URL to forward to if confirmation returns true
 */
function confirmDelete(url) {
  if (confirm('Are you sure?')) {
    window.location = url;
  }
}

function confirmForward(url) {
  if (confirm('Are you sure?')) {
    window.location = url;
  }
}

function confirmAction() {
  if (confirm('Are you sure?')) {
    return true;
  } else {
    return false;
  }
}
