require embedded-compositor-common.inc

SUMMARY = "Embedded Compositor - A Qt Wayland-based compositor suited for industrial HMIs"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE.GPLv3;md5=1ebbd3e34237af26da5dc08a4e440464"

# set git commit to checkout
SRCREV = "9190c8eb5013c8b6c2642c2d209fda6eed7b438e"

PR = "r0"
PV = "0.0.9+git${SRCPV}"

SRC_URI = "git://github.com/basysKom/embedded-compositor.git;protocol=https;branch=development"

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

QMAKE_PROFILES = "${S}/compositor.pro"
SYSTEMD_SERVICE:${PN} = "${SERVICE_FILE_NAME}"


FILES:${PN} += "\
                ${EXAMPLES_EXECUTABLES}/bottomclient \
                ${EXAMPLES_EXECUTABLES}/leftclient \
                ${EXAMPLES_EXECUTABLES}/quickcenterclient \
                ${EXAMPLES_EXECUTABLES}/rightclient \
                ${EXAMPLES_EXECUTABLES}/topclient \
                ${EXAMPLES_EXECUTABLES}/widgetcenterclient \
                ${libdir}/plugins/wayland-shell-integration/libshellintegration.so* \
                ${libdir}/liblibembeddedplatform.so.* \
               "

FILES:${PN}-dev +=  "\
                     ${libdir}/libembeddedplatform.so \
                     ${EMBEDDED_SHELL_BASE_DIR}/libquickembeddedshellwindow.so \
                     ${EMBEDDED_SHELL_BASE_DIR}/libquickembeddedshellwindow.so.1 \
                     ${EMBEDDED_SHELL_BASE_DIR}/libquickembeddedshellwindow.so.1.0 \
                     ${EMBEDDED_SHELL_BASE_DIR}/libquickembeddedshellwindow.so.1.0.0 \
                     ${EMBEDDED_SHELL_BASE_DIR}/qmldir \
                    "

FILES:${PN}-src += "\
                    ${HEADERS_BASE_DIR}/compositor/*.h \
                    ${HEADERS_BASE_DIR}/embeddedplatform/*.h \
                    ${HEADERS_BASE_DIR}/quickembeddedshellwindow/*.h \
                    ${HEADERS_BASE_DIR}/shellintegration/*.h \
                   "

FILES:${PN}-staticdev +=    "\
                             ${libdir}/libembeddedplatform.prl \
                             ${EMBEDDED_SHELL_BASE_DIR}/libquickembeddedshellwindow.prl \
                            "

BBCLASSEXTEND="native nativesdk"
