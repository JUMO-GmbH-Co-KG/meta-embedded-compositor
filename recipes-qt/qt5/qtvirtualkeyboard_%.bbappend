FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://0001-broken-character-being-inserted-on-wayland.patch \
    file://0002-Only-reparent-the-InputPanel.patch \
"
