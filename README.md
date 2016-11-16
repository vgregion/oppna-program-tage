# Tage
This application is used to donate and request furnitures and equipment for reuse within the Region of Västra Götaland in Sweden.

The source code is originally donated from Göteborg Stad.

## Development
### Prerequisites
Make sure the following components are installed:

* Java SDK 7+
* Maven 3
* Liferay Portal 6.2 EE Tomcat 7 bundle (This is the tested and verified version. Any 6.x version may work. Any other version is less likely to work out-of-the-box)

Tomcat needs a datasource named `jdbc/HotellDbPool` configured. See [instructions](https://github.com/Vastra-Gotalandsregionen/oppna-program/wiki/Anvisningar_JNDI_LiferayTomcat).

The Liferay theme used is [oppna-program-rp-theme-3](https://github.com/Vastra-Gotalandsregionen/oppna-program-rp-theme-3). To get the same look and feel the theme should be built, deployed and configured for the page where the application will reside.

### Build
After cloning the git repository, execute:

	mvn package

And copy the tage-portlet.war file to the Liferay deploy directory.

Tage is part of the Region of Västra Götaland's commitment to open source within the scope of [Öppna program](http://vastra-gotalandsregionen.github.io/oppna-program/).