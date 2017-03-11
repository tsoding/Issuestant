# Contribution Guidelines

Please keep in mind the following things while contributing to Issuestant project

- PR should pass all of the CI checks (Compilation, Unit Tests, [Wartremover] linting, Code Coverage threshold) and reviewed by the Project Leader,
- Changing configuration of [Wartremover] linting (including but not limited to suppressing particular Warts inline) is allowed only to the Project Leader.
- Any [Wartremover] linting configuration concerns should be filed as separate issues.
- If you change the REST API of Issuestant always update the corresponding Swagger Specification accordingly: [docs/issuestant-api.yaml]

Current Project Leader is [@rexim]

[Wartremover]: https://github.com/puffnfresh/wartremover
[@rexim]: https://github.com/rexim
