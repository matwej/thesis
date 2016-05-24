# Diploma thesis
- Standalone Spring boot application as a module communicating with API on messaging
- Used as automated tester for C, Java programs of basic programming tasks
- Unit testing, input/output run testing, static analysis
- Should be easily modifiable, new lang added

## Prerequisites
- Linux system
- run on user with limited privileges on system and limited home dir space
- gradle installed and on path of app user - http://gradle.org/
- gcc installed
- cppcheck installed and on path of app user - http://cppcheck.sourceforge.net/
- check installed - https://libcheck.github.io/check/ (follow the instructions to have it as global, linkable)
- checkmk on path of app user - http://micah.cowan.name/projects/checkmk/
- unzip dir with prototypes (prototypes.zip) in desired path, where app user can read them

## Running
- classic gradle run inside/outside IDE
- gradle build and then java -jar build/libs/thesis.jar

## Settings
- profiles: dev, prod (-Dspring.profiles.active=)
- run test timeout in seconds: 20, change with `--general.run_timeout=`
- one unit test timeout (not suite) in seconds: 30, change with `--general.unit_timeout=`
- directory for testing, default: `/tmp/test/`, change with `--general.operations_dir=` or OS env var `TESTER_OP_DIR`
- directory with prototypes, default: `/opt/prototypes/`, change with `--general.prototypes_dir=`, or OS env var `TESTER_PROTO_DIR`
- do not change prototypes dir names!!! (if yes, change code accordingly)

## Operation
- operating queues on default exchange of broker: Solution, Result, Assignment

## API
