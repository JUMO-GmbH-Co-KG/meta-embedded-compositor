addhandler embedded_compositor_bbappend_distrocheck
embedded_compositor_bbappend_distrocheck[eventmask] = "bb.event.SanityCheck"
python embedded_compositor_bbappend_distrocheck() {
    skip_check = e.data.getVar('SKIP_META_EMBEDDED_COMPOSITOR_SANITY_CHECK') == "1"
    if 'wayland' not in e.data.getVar('DISTRO_FEATURES').split() and not skip_check:
        bb.warn("You have included the meta-embedded-compositor layer, but \
'wayland' has not been enabled in your DISTRO_FEATURES. \
See the meta-embedded-compositor README.md for details on enabling wayland support for your \
platform.")
}
