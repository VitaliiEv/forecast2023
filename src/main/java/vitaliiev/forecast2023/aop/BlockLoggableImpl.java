package vitaliiev.forecast2023.aop;

import com.github.sidssids.blocklogger.spring.annotation.BlockLoggable;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;

import java.lang.annotation.Annotation;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@RequiredArgsConstructor
public class BlockLoggableImpl implements BlockLoggable {


    private final String loggerName;

    @Override
    public String[] value() {
        return new String[0];
    }

    @Override
    public String[] argNames() {
        return new String[0];
    }

    @Override
    public String loggerName() {
        return loggerName;
    }

    @Override
    public Class loggerClass() {
        return Object.class;
    }

    @Override
    public String title() {
        return "";
    }

    @Override
    public boolean appendClassName() {
        return false;
    }

    @Override
    public boolean appendArgs() {
        return true;
    }

    @Override
    public boolean appendResult() {
        return true;
    }

    @Override
    public boolean appendExceptionInfo() {
        return true;
    }

    @Override
    public boolean appendStackTrace() {
        return false;
    }

    @Override
    public Class<? extends Throwable>[] ignoreExceptions() {
        return new Class[0];
    }

    @Override
    public Level level() {
        return Level.DEBUG;
    }

    @Override
    public int maxElements() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return BlockLoggable.class;
    }
}
