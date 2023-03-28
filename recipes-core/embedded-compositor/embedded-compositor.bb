SUMMARY = "Embedded Compositor - A Qt Wayland-based compositor suited for industrial HMIs"
LICENSE = "GPLv3 & LGPLv3"
LIC_FILES_CHKSUM = "\
    file://LICENSE.GPLv3;md5=1ebbd3e34237af26da5dc08a4e440464 \
    file://LICENSE.LGPLv3;md5=9d5fd3dc9dd7a9225a53a8123d0360c5 \
"

SRCREV = "c816fc02bd5cd4b72442d5aaa4919830c0cf66e3"

PR = "r0"
PV = "0.0.9+git${SRCPV}"

SRC_URI = "git://github.com/JUMO-GmbH-Co-KG/embedded-compositor.git;protocol=https;nobranch=1 \
           file://${BPN}-env-client \
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

DEPENDS += " \
      qtbase \
      qtwayland \
      qtwayland-native \
"

RDEPENDS:${PN} = " \
      dbus \
      qtdeclarative \
      qtdeclarative-qmlplugins \
      qtdeclarative-tools \
      qtgraphicaleffects-qmlplugins \
      qtsvg-plugins \
      qtquickcontrols \
"

QMAKE_PROFILES = "${S}/embedded-compositor.pro"

do_install:append() {
  install -d ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${PN}.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${PN}-bottomclient.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${PN}-leftclient.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${PN}-quickcenterclient.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${PN}-rightclient.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${PN}-topclient.service ${D}${systemd_system_unitdir}
  install -m 0644 ${WORKDIR}/${PN}-widgetcenterclient.service ${D}${systemd_system_unitdir}

  install -d ${D}${sysconfdir}/default
  install -m 0644 ${WORKDIR}/embedded-compositor-env-client ${D}${sysconfdir}/default/
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
                            ${datadir}/embeddedcompositor-examples/bottomclient \
                            ${datadir}/embeddedcompositor-examples/leftclient \
                            ${datadir}/embeddedcompositor-examples/quickcenterclient \
                            ${datadir}/embeddedcompositor-examples/rightclient \
                            ${datadir}/embeddedcompositor-examples/topclient \
                            ${datadir}/embeddedcompositor-examples/widgetcenterclient \
                            ${sysconfdir}/default/${PN}-env-client \
                            ${systemd_system_unitdir}/${PN}-bottomclient.service \
                            ${systemd_system_unitdir}/${PN}-leftclient.service \
                            ${systemd_system_unitdir}/${PN}-quickcenterclient.service \
                            ${systemd_system_unitdir}/${PN}-rightclient.service \
                            ${systemd_system_unitdir}/${PN}-topclient.service \
                            ${systemd_system_unitdir}/${PN}-widgetcenterclient.service \
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

BBCLASSEXTEND = "native nativesdk"
