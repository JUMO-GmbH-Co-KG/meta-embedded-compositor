SUMMARY = "Embedded Compositor - A Qt Wayland-based compositor suited for industrial HMIs"
LICENSE = "GPLv3 & LGPLv3"
LIC_FILES_CHKSUM = "\
    file://LICENSE.GPLv3;md5=1ebbd3e34237af26da5dc08a4e440464 \
    file://LICENSE.LGPLv3;md5=9d5fd3dc9dd7a9225a53a8123d0360c5 \
"

SRCREV = "39c21da46bd312ea8484069a4cede9460b0a734a"

PR = "r0"
PV = "1.0.0+git${SRCPV}"

SRC_URI = "git://github.com/JUMO-GmbH-Co-KG/embedded-compositor.git;protocol=https;nobranch=1 \
           file://env/wayland-client \
           file://env/xdg-path \
           file://env/screen-orientation \
           file://${BPN}.service \
           file://${BPN}-bottomclient.service \
           file://${BPN}-leftclient.service \
           file://${BPN}-quickcenterclient.service \
           file://${BPN}-rightclient.service \
           file://${BPN}-topclient.service \
           file://${BPN}-widgetcenterclient.service \
           "

S = "${WORKDIR}/git"

inherit qmake5 systemd

DEPENDS = " \
      dbus \
      qtbase \
      qtwayland \
      qtdeclarative \
      qtwayland-native \
      "

DEPENDS:append:class-native = "\
      qtbase-native \
      "

DEPENDS:append:class-nativesdk = "\
      qtbase-native \
      "

RDEPENDS:${PN}:class-target = " \
      qtdeclarative-qmlplugins \
      qtdeclarative-tools \
      qtgraphicaleffects-qmlplugins \
      qtsvg-plugins \
      qtquickcontrols \
      "

TARGET_CFLAGS:class-target += " -DUSE_SYSTEM_BUS"

QMAKE_PROFILES = "${S}/${BPN}.pro"

do_install:append() {
  install -d ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${BPN}-bottomclient.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${BPN}-leftclient.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${BPN}-quickcenterclient.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${BPN}-rightclient.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${BPN}-topclient.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${BPN}-widgetcenterclient.service ${D}${systemd_system_unitdir}

  install -d ${D}${datadir}/${BPN}-testclients/

  install -d ${D}${sysconfdir}/default/${BPN}
  install -m 0644 ${WORKDIR}/env/wayland-client ${D}${sysconfdir}/default/${BPN}/
  install -m 0644 ${WORKDIR}/env/xdg-path ${D}${sysconfdir}/default/${BPN}/
  install -m 0644 ${WORKDIR}/env/screen-orientation ${D}${sysconfdir}/default/${BPN}/
}

do_install:append:class-nativesdk() {
  install -d ${D}${SDKPATHNATIVE}/opt/
  install -d ${D}${SDKPATHNATIVE}/opt/${BPN}/

  # using custom sdk path of all embedded-compositor files
  mv ${D}/usr/bin ${D}${SDKPATHNATIVE}/opt/${BPN}/
  mv ${D}/usr/lib ${D}${SDKPATHNATIVE}/opt/${BPN}/
}

SYSTEMD_PACKAGES ="${PN} ${PN}-demo-clients"

SYSTEMD_SERVICE:${PN} = "${PN}.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

SYSTEMD_SERVICE:${PN}-demo-clients = " \
                                      ${PN}-bottomclient.service \
                                      ${PN}-leftclient.service \
                                      ${PN}-quickcenterclient.service \
                                      ${PN}-rightclient.service \
                                      ${PN}-topclient.service \
                                      ${PN}-widgetcenterclient.service \
                                      "
SYSTEMD_AUTO_ENABLE:${PN}-demo-clients = "disable"

PACKAGES =+ "${PN}-demo-clients"

FILES_SOLIBSDEV:${PN}:append = " ${libdir}/lib*.so.1 ${libdir}/lib*.so.*.0"
FILES:${PN}-dev += " \
                    ${libdir}/qml/EmbeddedShell/libquickembeddedshellwindow.so \
                    ${libdir}/qml/EmbeddedShell/libquickembeddedshellwindow.so.1 \
                    ${libdir}/qml/EmbeddedShell/libquickembeddedshellwindow.so.*.0 \
                  "

FILES:${PN}-demo-clients += " \
                            ${datadir}/${BPN}-testclients/bottomclient \
                            ${datadir}/${BPN}-testclients/leftclient \
                            ${datadir}/${BPN}-testclients/quickcenterclient \
                            ${datadir}/${BPN}-testclients/rightclient \
                            ${datadir}/${BPN}-testclients/topclient \
                            ${datadir}/${BPN}-testclients/widgetcenterclient \
                            ${systemd_system_unitdir}/${BPN}-bottomclient.service \
                            ${systemd_system_unitdir}/${BPN}-leftclient.service \
                            ${systemd_system_unitdir}/${BPN}-quickcenterclient.service \
                            ${systemd_system_unitdir}/${BPN}-rightclient.service \
                            ${systemd_system_unitdir}/${BPN}-topclient.service \
                            ${systemd_system_unitdir}/${BPN}-widgetcenterclient.service \
                            "

FILES:${PN}-staticdev += " \
                          ${libdir}/libembeddedplatform.prl \
                          ${libdir}/qml/EmbeddedShell/libquickembeddedshellwindow.prl \
                         "

FILES:${PN} += " \
               ${libdir}/plugins/wayland-shell-integration/libshellintegration.so* \
               ${libdir}/qml/EmbeddedShell/libquickembeddedshellwindow.so* \
               ${libdir}/qml/EmbeddedShell/qmldir \
              "

FILES:${PN}:class-nativesdk = "${SDKPATHNATIVE}"

BBCLASSEXTEND = "native nativesdk"
