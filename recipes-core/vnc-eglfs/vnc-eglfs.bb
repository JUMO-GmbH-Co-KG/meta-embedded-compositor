SUMMARY = "this tool is written in Qt + QtQuick and visuals the mouse and touch events seen by application"
LICENSE = "CLOSED"
#LIC_FILE_CHKSUM="file://LICENSE;md5=987654"

SRC_URI = "git://git@github.com/JUMO-GmbH-Co-KG/vnc-eglfs.git;protocol=ssh;nobranch=1"

SRCREV = "f7ac8d5887dec4faac8d02b47561dedf2785a4ec"


DEPENDS += "\
    qtbase \
    pkgconfig \
    pkgconfig-native \
    openssl \
    openssl-native \
    "

inherit qmake5

OE_QMAKE += " INSTALL_ROOT=/usr/ "


S = "${WORKDIR}/git"

INSANE_SKIP:${PN}-dev += "dev-elf"


FILES:${PN}-dev += " /usr/plugins/platforms/libvncproxy.so"