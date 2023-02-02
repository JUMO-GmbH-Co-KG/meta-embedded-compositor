SUMMARY = "Embedded-Compositor DBUS policy rule"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r0"

SRC_URI = "file://embedded-compositor-dbus.conf"
S = "${WORKDIR}"


do_install() {
	install -d ${D}${sysconfdir}/dbus-1/system.d/
	install -m 0644 ${WORKDIR}/embedded-compositor-dbus.conf ${D}${sysconfdir}/dbus-1/system.d/
}
