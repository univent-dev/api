package com.univent.api

import org.springframework.modulith.core.ApplicationModules
import kotlin.test.Test

class ModulithTest {

    // 메인 클래스를 기준으로 모듈 구조를 분석합니다.
    private val modules = ApplicationModules.of(ApiApplication::class.java)

    @Test
    fun verifyModules() {
        // 이 한 줄이 핵심입니다.
        // 모듈 간의 부적절한 의존성(internal 참조, 순환 참조 등)이 발견되면 테스트가 실패합니다.
        modules.verify()
    }

    @Test
    fun writeDocumentation() {
        // 이 코드를 추가하면 build/modulith 디렉토리에
        // 현재 프로젝트의 모듈 의존성 다이어그램(PlantUML 등)이 자동 생성됩니다.
        org.springframework.modulith.docs.Documenter(modules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml()
    }
}
