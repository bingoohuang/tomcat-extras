package com.github.bingoohuang.tomcat.extras.listeners;

import com.github.bingoohuang.tomcat.extras.utils.Utils;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.deploy.NamingResourcesImpl;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.descriptor.web.ContextEnvironment;

/**
 * A listener which creates an environment entry with the name of the current context to populate
 * Logback's context name fully dynamic in a webapp's {@code logback.xml} file.
 * <p>
 * The default entry name is {@code logback/contextName}.
 * </p>
 */
public class LogbackContextNameListener implements LifecycleListener {
    private Log log = LogFactory.getLog(LogbackContextNameListener.class);
    private String name = "logback/contextName";

    @Override
    public void lifecycleEvent(LifecycleEvent le) {
        if (!le.getType().equals(Lifecycle.BEFORE_START_EVENT)) return;
        if (!(le.getLifecycle() instanceof Context)) return;

        Context context = (Context) le.getLifecycle();

        NamingResourcesImpl namingResources = context.getNamingResources();
        ContextEnvironment contextEnvironment = namingResources.findEnvironment(name);
        if (contextEnvironment != null) namingResources.removeEnvironment(name);

        contextEnvironment = new ContextEnvironment();
        contextEnvironment.setName(name);
        contextEnvironment.setOverride(false);
        contextEnvironment.setType("java.lang.String");
        String contextName = Utils.getContextName(context);
        contextEnvironment.setValue(contextName);

        if (log.isDebugEnabled()) {
            String fmt = "Adding Logback context name env entry '%s' to context '%s'";
            String msg = String.format(fmt, name, contextName);
            log.debug(msg);
        }

        namingResources.addEnvironment(contextEnvironment);
    }

    public void setName(String name) {
        if (Utils.isEmpty(name))
            throw new IllegalArgumentException("Parameter 'name' cannot be empty");

        this.name = name;
    }
}
