/*
 * Copyright (c) 2016, OctopusMC and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of OctopusMC or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package net.octopusmc.anvil.version;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.octopusmc.anvil.version.impl.Wrapper1_8_R3;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Contains all of the {@link VersionWrapper}s
 * @author ElieTGM
 * @since 1.0
 */
public enum Version {

    /**
     * The {@link Wrapper1_8_R3} value
     */
    ONE_EIGHT_R3("1_8_R3", Wrapper1_8_R3.class);


    /**
     * A {@link LoadingCache} of VersionWrappers that are kept until 5 minutes of no use
     */
    private static final LoadingCache<Class<? extends VersionWrapper>, VersionWrapper> WRAPPER_CACHE =
            CacheBuilder.newBuilder()
                    .maximumSize(values().length)
                    .expireAfterWrite(5, TimeUnit.MINUTES)
                    .build(new CacheLoader<Class<? extends VersionWrapper>, VersionWrapper>() {
                        @Override
                        public VersionWrapper load(Class<? extends VersionWrapper> aClass) throws Exception {
                            return aClass.newInstance();
                        }
                    });

    /**
     * The package value of this NMS version
     */
    private final String pkg;
    /**
     * The {@link VersionWrapper} class for this NMS version
     */
    private final Class<? extends VersionWrapper> wrapper;

    /**
     * Creates a new value Version value
     * @param pkg The package value of this NMS version
     * @param wrapper The {@link VersionWrapper} class for this NMS version
     */
    Version(String pkg, Class<? extends VersionWrapper> wrapper) {
        this.pkg = pkg;
        this.wrapper = wrapper;
    }

    /**
     * Gets the package value of this NMS version
     * @return The package value
     */
    public String getPkg() {
        return pkg;
    }

    /**
     * Gets the {@link VersionWrapper} for this NMS version
     * @return The {@link VersionWrapper} for this NMS version
     */
    public VersionWrapper getWrapper() {
        try {
            return WRAPPER_CACHE.get(wrapper);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds the {@link Version} from the NMS package value
     * @param pkg The NMS package value
     * @return The {@link Version}, or null if no version is found
     */
    public static Version of(final String pkg) {
        return Arrays.stream(values()).filter(ver -> pkg.equals("v" + ver.getPkg())).findFirst().orElse(null);
    }

}
