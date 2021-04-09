package io.github.supercheese200.blacklistbypass;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class BypassAgent implements ClassFileTransformer {
    /**
     * Pre-main - Called when the agent is invoked at the startup of a JVM.
     * @param agentArgs The launch arguments of the Java agent
     * @param instrumentation An Instrumentation instance, allowing us to add transformers.
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new BypassAgent());
    }

    /**
     * Transformation method - implemented by a ClassFileTransformer in order to change classes' bytecode as they are loaded.
     * @param classLoader The ClassLoader of the class being transformed.
     * @param className The class name passed from the JVM.
     * @param classBeingTransformed The Class object of the class being transformed.
     * @param protectionDomain The protection domain of the class being transformed.
     * @param bytes The bytecode of the class being transformed.
     * @return The redefined array of bytes.
     * @throws IllegalClassFormatException
     */
    @Override
    public byte[] transform(ClassLoader classLoader, String className, Class<?> classBeingTransformed, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
        try {
            if (className != null) {
                className = className.replace("/", ".");
                if (className.equals("io.netty.bootstrap.Bootstrap") || className.equals("com.mojang.patchy.BlockedServers")) {
                    ClassPool pool = ClassPool.getDefault();
                    pool.appendClassPath(new ByteArrayClassPath(className, bytes));

                    CtClass ctClass = pool.get(className);
                    CtMethod method = ctClass.getMethod("isBlockedServerHostName", "(Ljava/lang/String;)Z");
                    method.setBody("{ return false; }");

                    System.out.println("[MojangBlacklistBypass] Successfully retransformed server blacklist!");
                    return ctClass.toBytecode();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
