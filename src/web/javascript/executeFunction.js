function executeFunction() {
	var functionName = executeFunction.arguments[0];
	var func = this[functionName];
	return func(executeFunction.arguments);
}

