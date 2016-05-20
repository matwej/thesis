package sk.fei.stuba.xpivarcim.db.entities;


import sk.fei.stuba.xpivarcim.db.entities.files.ModuleFile;
import sk.fei.stuba.xpivarcim.db.entities.files.SourceFile;
import sk.fei.stuba.xpivarcim.db.entities.files.TestFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
    @Transient
    private int status;

    public Assignment() {
    }

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
        if (!(sourceFiles == null))
            for (ModuleFile f : sourceFiles) {
                f.setAssignment(this);
            }
        if (!(testFiles == null))
        for (ModuleFile f : testFiles) {
            f.setAssignment(this);
        }
    }

    public Set<TestFile> runTestFiles() {
        Set<TestFile> output = new HashSet<>();
        for (TestFile f : testFiles)
            if (f.isRunTest()) output.add(f);
        return output;
    }

    public Set<TestFile> unitTestFiles() {
        Set<TestFile> output = new HashSet<>();
        for (TestFile f : testFiles)
            if (!f.isRunTest()) output.add(f);
        return output;
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