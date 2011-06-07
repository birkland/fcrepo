
package org.fcrepo.server.storage.highlevel;

import org.fcrepo.server.storage.types.BasicDigitalObject;
import org.fcrepo.server.storage.types.Datastream;
import org.fcrepo.server.storage.types.DatastreamManagedContent;
import org.fcrepo.server.storage.types.DigitalObject;

/**
 * DigitalObject that will use high level storage for retrieving managed
 * datastream content.
 * <p>
 * The Fedora deserializers create instances of {@link DatastreamManagedContent}
 * when they deserialize managed satastreams and populate a
 * {@link DigitalObject}. Unfortunately, <code>DatastreamManagedContent</code>
 * uses low level storage semantics to retrieve the underlying content stream.
 * This class extends {@link BasicDigitalObject} and wraps every managed
 * datastream with a {@link HighlevelStorage} accessor.
 * </p>
 */
public class HighlevelDigitalObject
        extends BasicDigitalObject {

    private final HighlevelStorage m_storage;

    public HighlevelDigitalObject(HighlevelStorage store) {
        super();
        m_storage = store;
    }

    @Override
    public void addDatastreamVersion(Datastream ds, boolean addNewVersion) {
        super.addDatastreamVersion(wrap(ds), addNewVersion);

    }

    private Datastream wrap(Datastream d) {
        if (d instanceof DatastreamManagedContent) {
            return new HighlevelDatastreamManagedContent(this, d, m_storage);
        } else {
            return d;
        }
    }
}