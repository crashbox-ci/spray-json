test:
	sbt +testsJVM/run
	sbt +testsJS/run
	sbt +testsNative/run

publishLocal:
	sbt +coreJVM/publishLocal
	sbt +coreJS/publishLocal
	sbt +coreNative/publishLocal

.PHONY: test publishLocal
