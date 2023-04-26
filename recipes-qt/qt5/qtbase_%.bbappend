PACKAGECONFIG_GL = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2', '', d)}"

RDEPENDS:${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'qtwayland', '', d)}"

PACKAGECONFIG += "\
    eglfs \
    gbm \
    kms \
    xkbcommon \
"

QT_CONFIG_FLAGS:append = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', ' -qpa wayland', ' -qpa eglfs', d)}"
