# Reference Project Rules

## Two reference projects, two different roles

### 1. Exercise project — authoritative for structure

The course-exercise project is the authoritative source for:
- project/module organization;
- package names and hierarchy;
- class/package placement;
- naming conventions;
- Client/CommonLib/Server organization;
- NetBeans/Ant project organization;
- general structural conventions.

It is NOT a complete seminar implementation. Missing functionality must still
be added when required by the specification.

### 2. Completed project — functional/implementation reference

The completed project has a different structure and package organization.
DO NOT copy or follow its package structure.

Use it as a functional reference for features missing from the exercise
project, especially:
- form/controller interaction;
- the pattern in which every GUI form has its own Controller;
- multiple Controllers;
- server-side configuration GUI;
- reading `.properties`;
- changing properties;
- writing updated values back to `.properties`;
- other complete implementation details.

Adapt those features to the exercise project's package structure.

## Priority

1. Specification: `CLAUDE.md` + `docs/` + supplied UML/relational model,
   constraints, use cases and contracts.
2. Exercise project: authoritative for structure.
3. Completed project: functional reference for missing features.

## Mandatory Controller rule

EVERY GUI form in the final application MUST have its own dedicated
Controller.

This includes Client forms and Server-side GUI/configuration forms.

The rule is:

    ONE FORM = ONE DEDICATED CONTROLLER

This does NOT mean one Controller per System Operation. A form's Controller
may invoke multiple SOs.

## Server properties requirement

The server configuration form must be able to:
- read the properties file;
- display the relevant values;
- allow changes;
- write the updated values back to the properties file.

The form must have its own Controller.

## NetBeans forms

Every Swing form must remain a NetBeans GUI Builder form with both:
- `.java`
- `.form`
