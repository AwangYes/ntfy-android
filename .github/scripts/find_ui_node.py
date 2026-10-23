#!/usr/bin/env python3
"""Print the screen coordinates of a UIAutomator node by text or resource id."""
import re
import sys
import xml.etree.ElementTree as ET

needle = sys.argv[1]
resource_suffix = sys.argv[2] if len(sys.argv) > 2 else None
root = ET.fromstring(sys.stdin.read())

for node in root.iter("node"):
    text = node.attrib.get("text", "")
    resource_id = node.attrib.get("resource-id", "")
    if text == needle or (resource_suffix and resource_id.endswith("/" + resource_suffix)):
        match = re.match(r"\[(\d+),(\d+)\]\[(\d+),(\d+)\]", node.attrib.get("bounds", ""))
        if match:
            x = (int(match.group(1)) + int(match.group(3))) // 2
            y = (int(match.group(2)) + int(match.group(4))) // 2
            print(f"{x} {y}")
            break
