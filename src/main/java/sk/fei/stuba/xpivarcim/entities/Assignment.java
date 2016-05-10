package sk.fei.stuba.xpivarcim.entities;


import sk.fei.stuba.xpivarcim.entities.files.ModuleFile;
import sk.fei.stuba.xpivarcim.entities.files.SourceFile;
import sk.fei.stuba.xpivarcim.entities.files.TestFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    private long id;
    @Column
    private String codeLanguage;
    @Column
    private Date lastUpdated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignment")
    private Set<SourceFile> sourceFiles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignment")
    private Set<TestFile> testFiles;

    private int status;

    public Assignment() {}

    public Assignment(long id) {
        this.id = id;
    }

    public Assignment(long id, String codeLanguage, Date lastUpdated, Set<SourceFile> sourceFiles, Set<TestFile> testFiles, int status) {
        this.id = id;
        this.codeLanguage = codeLanguage;
        this.lastUpdated = lastUpdated;
        this.sourceFiles = sourceFiles;
        this.testFiles = testFiles;
        this.status = status;
    }

    public void setFiles() {
        for(ModuleFile f : sourceFiles) {
            f.setAssignment(this);
        }
        for(ModuleFile f : testFiles) {
            f.setAssignment(this);
        }
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getId() {
        return id;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getCodeLanguage() {
        return codeLanguage;
    }

    public Set<SourceFile> getSourceFiles() {
        return sourceFiles;
    }

    public Set<TestFile> getTestFiles() {
        return testFiles;
    }

    public int getStatus() {
        return status;
    }
}
