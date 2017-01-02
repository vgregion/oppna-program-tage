# Tage
This application is used to donate and request furniture and equipment for reuse within the Region of Västra Götaland in Sweden. In that way the organisation and its divisions may contribute to more effective use of resources and improved sustainability.

The employees are allowed to create *advertisements* where they offer pieces of furniture for others to take over. It is also possible to create *requests* where they outline something they wish for.

Before Tage was first used many employees reacted to the amount of furniture which was thrown away while moving or rebuilding departments. The departments usually don't have own places to store furniture and missing access to centrally located storage.

The source code is originally donated from Göteborg Stad.

Follow this [link](http://www.vgregion.se/sv/Vastra-Gotalandsregionen/startsida/Miljo/Prioriterade-miljoomraden/Hallbara-produkter-och-tjanster/Tage---intern-bytessajt-for-mobler/) for more information, in Swedish.

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