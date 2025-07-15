# Device Descriptions for Unit and Integration Tests

## Descriptions

Device descriptions used by unit and integration tests are configured in `devicedescriptions.yaml`
and must contain the EID file name and the required configuration parameters.


## EID XML

**Note:** The _schemaLocation_ must be changed to the relative path where the XSD schemas are located!

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="/xsl/SGr.xsl"?>
<DeviceFrame xmlns="http://www.smartgridready.com/ns/V0/"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.smartgridready.com/ns/V0/ ../../../../../SGrSpecifications/SchemaDatabase/SGr/Product/Product.xsd">

  ...

</DeviceFrame>
```
