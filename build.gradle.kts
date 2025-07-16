plugins {
    java
    id("org.springframework.boot") version "3.5.3"          // 안정판
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.bookshop"
version = "0.0.1-SNAPSHOT"

val springCloudVersion = "2023.0.1" //사용할 클라우드 버전

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation ("org.springframework.cloud:spring-cloud-config-server")
    testImplementation ("org.assertj:assertj-core")
}

dependencyManagement {
    imports {//스프링 클라우드 의존성 관리를 위한 DOM
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.1")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}
tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-java-tiny:0.0.46")
    //imagePlatform.set("linux/arm64")
    imageName.set(project.name)
    imageName.set("ghcr.io/kingstree/${project.name}:latest")   // ★ 레지스트리·계정 포함
    environment.put("BP_JVM_VERSION", "17")

    docker {
        publishRegistry {
            username = project.findProperty("registryUsername") as String?
            password = project.findProperty("registryToken") as String?
            url = project.findProperty("registryUrl") as String?
        }
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}
