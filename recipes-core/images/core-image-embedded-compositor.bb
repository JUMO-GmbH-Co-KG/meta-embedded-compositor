SUMMARY = "Embedded-Compositor base image"

LICENSE = "MIT"

inherit core-image populate_sdk_qt5


IMAGE_INSTALL += "\
    packagegroup-core-boot \
    dbus-broker \
    embedded-compositor-dbus-rules \
    embedded-compositor \
    qtbase \
    qtbase-plugins \
    qtdeclarative \
    qtdeclarative-qmlplugins \
    qtdeclarative-tools \
    qtscript \
    qtgraphicaleffects-qmlplugins \
    qtsvg \
    qtsvg-plugins \
    "
