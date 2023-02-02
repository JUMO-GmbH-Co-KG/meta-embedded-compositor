Embedded-Compositor OpenEmbedded/Yocto Project layer
====================================================
OpenEmbedded layer to add support for an embedded compositor suited for industrial HMIs.

## Supported Platforms
* Wayland based window system suited for embedded architectures with the aim to be easier to develop, extend, and maintain.

License
=======
This layer is distributed under the MIT license if not noted otherwise.
This includes all recipes, configuration files and meta data created by
JUMO GmbH & Co. KG. Source code included in the tree is distributed under the
license stated in the corresponding recipe or as mentioned in the code.
There is some work of others companies included or referenced.
Attribution is kept as required. The receipt meta data is mostly MIT,
if not noted otherwise. The binaries and code compiled for the target
rootfs is distributed under the vendors license. The licenses are
provided in the /licenses subdirectory to be collected by bitbake.

## Dependencies
This layer depends on packages provided by the following layers:
* `meta-openembedded` [http://cgit.openembedded.org/meta-openembedded/]
* `meta-qt5` [https://github.com/meta-qt5/meta-qt5.git]

Configuration
=============
1. Clone the `meta-embedded-compositor` layer to your project directory.
2. Add the `meta-embedded-compositor` layer to `conf/bblayers.conf`
```bitbake
    BBLAYERS += "path/to/meta-embedded-compositor"
```
3. Add dependency layers to `conf/bblayers.conf`
```bitbake
    BBLAYERS += "path/to/meta-openembedded/meta-oe"
    BBLAYERS += "path/to/meta-qt5"
```

Usage
=====

To build an core image for your Platform:
```shell
	MACHINE=<your machine> bitbake core-image-embedded-compositor
```

Alternatively to add support for a given platform into your image add the following to your DISTRO conf `conf/local.conf`

```bitbake
    CORE_IMAGE_EXTRA_INSTALL += "embedded-compositor"
```
