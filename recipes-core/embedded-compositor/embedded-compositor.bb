SUMMARY = "Embedded Compositor - A Qt Wayland-based compositor suited for industrial HMIs"
LICENSE = "GPLv3 & LGPLv3"
LIC_FILES_CHKSUM = "\
    file://LICENSE.GPLv3;md5=1ebbd3e34237af26da5dc08a4e440464 \
    file://LICENSE.LGPLv3;md5=9d5fd3dc9dd7a9225a53a8123d0360c5 \
"

SRCREV = "e8cd6e593b3977fc76756320ce57aa19239d7f35"

PR = "r0"
PV = "1.0.2+git${SRCPV}"

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

  install -d ${D}${sysconfdir}/default/${BPN}
  install -m 0644 ${WORKDIR}/env/wayland-client ${D}${sysconfdir}/default/${BPN}/
  install -m 0644 ${WORKDIR}/env/xdg-path ${D}${sysconfdir}/default/${BPN}/
  install -m 0644 ${WORKDIR}/env/screen-orientation ${D}${sysconfdir}/default/${BPN}/

  # add needed header files to sdk
  install -d ${D}${includedir}/${BPN}
  cp -f ${S}/embeddedplatform/embeddedplatform.h ${D}${includedir}/${BPN}
  cp -f ${S}/embeddedplatform/embeddedshellanchor.h ${D}${includedir}/${BPN}
  cp -f ${S}/embeddedplatform/embeddedshellsurface.h ${D}${includedir}/${BPN}
}

do_install:append:class-nativesdk() {
  install -d ${D}${SDKPATHNATIVE}/opt/
  install -d ${D}${SDKPATHNATIVE}/opt/${BPN}/

  # using custom sdk path of all embedded-compositor files
  mv ${D}/usr/bin ${D}${SDKPATHNATIVE}/opt/${BPN}/
  mv ${D}/usr/lib ${D}${SDKPATHNATIVE}/opt/${BPN}/
  mv ${D}/usr/share ${D}${SDKPATHNATIVE}/opt/${BPN}/
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

INSANE_SKIP:${PN} += "dev-so"
FILES_SOLIBSDEV = ""

FILES:${PN}-dev += " \
                    ${includedir}/${BPN} \
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

FILES:${PN} += " \
               ${libdir}/libembeddedplatform.prl \
               ${libdir}/libembeddedplatform.so \
               ${libdir}/qml/EmbeddedShell/libquickembeddedshellwindow.prl \
               ${libdir}/qml/EmbeddedShell/libquickembeddedshellwindow.so* \
               ${libdir}/plugins/wayland-shell-integration/libshellintegration.so* \
               ${libdir}/qml/EmbeddedShell/qmldir \
              "

FILES:${PN}:class-nativesdk = "${SDKPATHNATIVE}"

BBCLASSEXTEND = "native nativesdk"
