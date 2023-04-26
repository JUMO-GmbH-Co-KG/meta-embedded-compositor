PACKAGECONFIG:class-native += " \
                    wayland-server \
                    wayland-client \
                    "

PACKAGECONFIG:class-nativesdk += " \
                    wayland-server \
                    wayland-client \
                    "

QMAKE_PROFILES:class-native = "${S}"
QMAKE_PROFILES:class-nativesdk = "${S}"
