package org.carlspring.strongbox.providers.layout;

import java.io.IOException;
import java.nio.file.Files;

import javax.annotation.PostConstruct;

import org.carlspring.strongbox.artifact.coordinates.NugetHierarchicalArtifactCoordinates;
import org.carlspring.strongbox.io.RepositoryFileSystemProvider;
import org.carlspring.strongbox.io.RepositoryPath;
import org.carlspring.strongbox.storage.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Layout provider for Nuget package repository.<br>
 * It provides hierarchical directory layout like follows: <br>
 * &lt;packageID&gt;<br>
 * └─&lt;version&gt;<br>
 * &emsp;├─&lt;packageID&gt;.&lt;version&gt;.nupkg<br>
 * &emsp;├─&lt;packageID&gt;.&lt;version&gt;.nupkg.sha512<br>
 * &emsp;└─&lt;packageID&gt;.nuspec<br>
 * 
 * 
 * @author Sergey Bespalov
 *
 */
@Component
public class NugetHierarchicalLayoutProvider extends AbstractLayoutProvider<NugetHierarchicalArtifactCoordinates>
{
    private static final Logger logger = LoggerFactory.getLogger(NugetHierarchicalLayoutProvider.class);

    public static final String ALIAS = "Nuget Hierarchical";

    @Override
    @PostConstruct
    public void register()
    {
        layoutProviderRegistry.addProvider(ALIAS, this);
        logger.info("Registered layout provider '" + getClass().getCanonicalName() + "' with alias '" + ALIAS + "'.");
    }

    @Override
    public String getAlias()
    {
        return ALIAS;
    }

    @Override
    public NugetHierarchicalArtifactCoordinates getArtifactCoordinates(String path)
    {
        return new NugetHierarchicalArtifactCoordinates(path);
    }

    @Override
    protected boolean isMetadata(String path)
    {
        return path.endsWith("nuspec");
    }

    @Override
    protected boolean isChecksum(String path)
    {
        return path.endsWith("nupkg.sha512");
    }

    @Override
    public void deleteMetadata(String storageId,
                               String repositoryId,
                               String metadataPath)
        throws IOException
    {

    }

    @Override
    protected void doDeletePath(RepositoryPath repositoryPath,
                                boolean force)
        throws IOException
    {
        RepositoryPath sha512Path = repositoryPath.resolveSibling(repositoryPath.getFileName() + ".sha512");
        
        Files.delete(repositoryPath);
        Files.deleteIfExists(sha512Path);
        
        Repository repository = repositoryPath.getFileSystem().getRepository();
        RepositoryFileSystemProvider provider = getProvider(repositoryPath);
        if (force && repository.allowsForceDeletion())
        {
            provider.deleteTrash(repositoryPath);
            provider.deleteTrash(sha512Path);
        }
    }

}