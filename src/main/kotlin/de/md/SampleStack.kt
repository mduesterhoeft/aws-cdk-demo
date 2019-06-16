package de.md

import software.amazon.awscdk.App
import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.apigateway.LambdaIntegration
import software.amazon.awscdk.services.apigateway.RestApi
import software.amazon.awscdk.services.apigateway.RestApiProps
import software.amazon.awscdk.services.lambda.*
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Runtime.*

class SampleStack(parent: App, name: String, stackProps: StackProps): Stack(parent, name, stackProps) {
    init {
        val sampleLambda = Function(
            this, "sampleLambda",
            withFunctionDefault {
                withFunctionName("sampleLambda")
                withHandler(SampleHandler::class.qualifiedName)
            }
        )

        RestApi(
            this, "sampleApi", RestApiProps.builder()
                .withRestApiName("sampleApi")
                .build()
        ).apply {
            root.addResource("some")
                .addMethod("GET", LambdaIntegration(sampleLambda))
        }
    }

    private fun withFunctionDefault(f: FunctionProps.Builder.() -> Unit): FunctionProps =
        FunctionProps.builder()
            .withRuntime(JAVA8)
            .withCode(Code.asset("build/libs/aws-cdk-demo.jar"))
            .withMemorySize(1024)
            .withTimeout(30)
            .apply { f() }
            .build()
}