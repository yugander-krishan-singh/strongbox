package org.carlspring.strongbox.storage.repository;

import org.carlspring.strongbox.configuration.ProxyConfiguration;
import org.carlspring.strongbox.storage.Storage;
import org.carlspring.strongbox.storage.repository.remote.RemoteRepository;
import org.carlspring.strongbox.xml.repository.CustomRepositoryConfiguration;

import java.util.List;
import java.util.Map;

public interface Repository
{

    String getId();

    String getBasedir();

    String getPolicy();

    String getImplementation();

    String getLayout();

    String getType();

    boolean isSecured();

    String getStatus();

    long getArtifactMaxSize();

    boolean isTrashEnabled();

    boolean allowsForceDeletion();

    boolean allowsDeployment();

    boolean allowsRedeployment();

    boolean allowsDeletion();

    boolean allowsDirectoryBrowsing();

    boolean isChecksumHeadersEnabled();

    ProxyConfiguration getProxyConfiguration();

    RemoteRepository getRemoteRepository();

    HttpConnectionPool getHttpConnectionPool();

    List<CustomConfiguration> getCustomConfigurations();

    CustomRepositoryConfiguration getRepositoryConfiguration();

    Map<String, String> getGroupRepositories();

    Map<String, String> getArtifactCoordinateValidators();

    Storage getStorage();

    boolean isHostedRepository();

    boolean isProxyRepository();

    boolean isGroupRepository();

    boolean isInService();

    boolean acceptsSnapshots();

    boolean acceptsReleases();

}