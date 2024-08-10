package com.example.demo.base;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * Base class for unit tests of service classes that use Mockito for mocking.
 *
 * This class is annotated with {@link ExtendWith} to integrate Mockito's extension into the JUnit test lifecycle.
 * It also uses {@link MockitoSettings} to configure Mockito's strictness level to {@link Strictness#LENIENT},
 * allowing for more flexible mock behavior and avoiding unnecessary stubbing warnings.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public abstract class AbstractBaseServiceTest {

}
