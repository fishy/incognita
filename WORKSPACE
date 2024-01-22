workspace(name = "incognita")

android_sdk_repository(
    name = "androidsdk",
    api_level = 33,
)

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive", "http_jar")

register_toolchains("//:kotlin_toolchain")

BUNDLE_TOOL_TAG = "1.15.6"

http_jar(
    name = "android_bundletool",
    integrity = "sha256-OK6KELzazvB+zOghEYjFySs3a+lto4/z7h8s9IlbLLg=",
    urls = [
        "https://github.com/google/bundletool/releases/download/%s/bundletool-all-%s.jar" % (BUNDLE_TOOL_TAG, BUNDLE_TOOL_TAG),
    ],
)

RULES_OPPIA_ANDROID_TAG = "v0.7"

http_archive(
    name = "rules_oppia_android",
    integrity = "sha256-YujR+ZeoprWtonJ25sV6hyte6vPIlUjVpFmBSE4UrJY=",
    strip_prefix = "oppia-android-%s" % RULES_OPPIA_ANDROID_TAG[1:],
    urls = [
        "https://github.com/oppia/oppia-android/archive/%s.zip" % RULES_OPPIA_ANDROID_TAG,
    ],
)
