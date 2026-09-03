# Final Implementation Checklist

Before considering the project complete, verify:

## Reference projects
- [ ] Exercise project structure/package names are followed.
- [ ] Completed project is used only as a functional implementation reference.
- [ ] Completed project's package structure is NOT copied.

## GUI / Controllers
- [ ] EVERY GUI form has exactly one dedicated Controller.
- [ ] No Controller is created solely because an SO exists.
- [ ] A Controller may call multiple SOs when its form requires them.
- [ ] Client forms have Controllers.
- [ ] Server/configuration forms have Controllers.
- [ ] Every Swing form has both `.java` and `.form`.

## Server configuration
- [ ] Server form reads the `.properties` file.
- [ ] Server form displays the relevant properties.
- [ ] User can modify them.
- [ ] Modified properties are written back to the `.properties` file.

## Scope
- [ ] Only SK1, SK2, SK3, SK5, SK6, SK7, SK8, SK9 and SK22 are implemented.
- [ ] Only their required SOs/supporting SOs are implemented.
- [ ] The eight supplied UG contracts are preserved.

## Domain/database
- [ ] UML composition/aggregation semantics are preserved.
- [ ] All supplied relational constraints are implemented.
- [ ] MySQL/MariaDB SQL is provided in `database/baza.sql`.
