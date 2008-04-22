-- LookupList.java did not display a default item if "-- None --" was added dynamically.
-- lookup_asset_status is the only drop-down whose behaviour will change due to the fix.
-- Hence revert the default attribute to false for backward compatibility
UPDATE lookup_asset_status SET default_item = false;
