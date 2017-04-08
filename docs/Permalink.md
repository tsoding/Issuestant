# Permalink

Issuestant automatically replaces all of the dynamic src-links with the permanent ones with the current latest commit hash. For example:

```
https://github.com/tsoding/Issuestant/blob/master/src/main/scala/me/rexim/Mixer.scala
```

should be replaced with

```
https://github.com/tsoding/Issuestant/blob/<latest-commit-hash>/src/main/scala/me/rexim/Mixer.scala
```

It requires to give Issuestant enough permissions to edit all of the comments and descriptions in a project. If after enabling this feature Issuestant doesn't have enough permissions, he files an issue about that and assigns it to the Project Leader for resolution.
