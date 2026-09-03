# NetBeans Forms — Mandatory Project Rule

The project will be developed with **NetBeans GUI Builder**.

The user will provide three complete reference projects. Claude Code must
inspect those projects before implementing the GUI.

## What to inspect

For each reference project, inspect:

- `src/`
- Swing form `.java` files
- corresponding `.form` files
- `nbproject/`
- `build.xml`
- package structure
- generated `initComponents()` sections
- event handlers
- layout/group definitions

## Required output

Every form that should be editable through NetBeans GUI Builder must have:

```text
FrmSomething.java
FrmSomething.form
```

The `.form` file is not optional.

The form must open in NetBeans in **Design** mode.

Do not create a plain Java Swing class and assume it is equivalent to a
NetBeans GUI Builder form.

## Three reference projects

The three supplied projects should be treated as the reference for the
implementation technique and GUI structure.

They do NOT override the reservation-system specification.

Use them to learn HOW to create:
- forms
- panels
- buttons
- labels
- tables
- combo boxes
- text fields
- layouts
- event handlers
- NetBeans-generated code

Use the reservation specification to decide:
- what forms exist
- what fields they contain
- what actions they perform
- what system operations they call
- what validations apply
- what messages are displayed

## Do not manually break GUI Builder compatibility

Avoid:
- deleting `.form` files
- manually replacing GUI Builder generated sections
- converting forms to a different UI framework
- introducing JavaFX
- introducing a web frontend
- introducing a custom GUI framework

unless the user explicitly requests it.

If custom code is required, keep it outside NetBeans-generated sections when
possible.

## Build and verify

After creating/changing a form:

1. verify `.java` + `.form` both exist;
2. verify package paths;
3. verify NetBeans project metadata;
4. build with the existing Ant/NetBeans project;
5. verify the form can be opened in NetBeans Design view.
