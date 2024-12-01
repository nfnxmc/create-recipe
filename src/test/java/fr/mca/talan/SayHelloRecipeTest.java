package fr.mca.talan;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

class SayHelloRecipeTest implements RewriteTest {
    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new SayHelloRecipe("fr.mca.talan.FooBar"));
    }

    @Test
    void addsHelloToFooBar() {
        rewriteRun(
            java(
                """
                    package fr.mca.talan;

                    class FooBar {
                    }
                """,
                """
                    package fr.mca.talan;

                    class FooBar {
                        public String hello() {
                            return "Hello from fr.mca.talan.FooBar!";
                        }
                    }
                """
            )
        );
    }

    @Test
    void doesNotChangeExistingHello() {
        rewriteRun(
            java(
                """
                    package fr.mca.talan;
        
                    class FooBar {
                        public String hello() { return ""; }
                    }
                """
            )
        );
    }

    @Test
    void doesNotChangeOtherClasses() {
        rewriteRun(
            java(
                """
                    package fr.mca.talan;
        
                    class Bash {
                    }
                """
            )
        );
    }
}