/*
 * MIT License
 *
 * Copyright (c) 2016 David Russell
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.gitpitch.utils;

import com.gitpitch.models.GitRepoModel;
import com.gitpitch.utils.PitchParams;
import play.Configuration;
import play.Logger;
import play.Logger.ALogger;

import java.util.Arrays;
import java.util.List;

/*
 * Rendering model for views.Landing.scala.html.
 */
public class GitRepoRenderer {

    private final Logger.ALogger log = Logger.of(this.getClass());

    private final PitchParams _pp;
    private final GitRepoModel _grm;
    private Configuration _cfg;
    /*
     * Relative URLs for view components.
     */
    private String _landingBase;
    private String _landingURL;
    private String _slideshowURL;
    private String _markdownURL;
    /*
     * Absolute URLs for GitHub page links.
     */
    private String _orgHub;
    private String _repoHub;
    private String _starHub;
    private String _forkHub;

    private GitRepoRenderer(PitchParams pp) {
        this(pp, null, null);
    }

    private GitRepoRenderer(PitchParams pp,
                            GitRepoModel grm,
                            Configuration cfg) {

        this._pp = pp;
        this._grm = grm;
        this._cfg = cfg;

        if (grm != null) {

            /*
             * Initialize properties based on valid GitHub repository
             * data returned on GitHub API as GitRepoModel, if exists.
             */

            this._landingURL = com.gitpitch.controllers.routes.PitchController
                    .landing(_grm.owner(),
                            _grm.name(),
                            _pp.branch,
                            _pp.theme,
                            _pp.notes, null).toString();

            this._slideshowURL = com.gitpitch.controllers.routes.PitchController
                    .slideshow(_grm.owner(),
                            _grm.name(),
                            _pp.branch,
                            _pp.theme,
                            _pp.notes, null, null).toString();

            this._markdownURL = com.gitpitch.controllers.routes.PitchController
                    .markdown(_grm.owner(),
                            _grm.name(),
                            _pp.branch).toString();

            this._orgHub = new StringBuffer(GIT_COM).append(_grm.owner())
                    .toString();

            this._repoHub =
                    new StringBuffer(_orgHub).append(SLASH)
                            .append(this._grm.name())
                            .toString();


            this._starHub = new StringBuffer(this._repoHub).append(SLASH)
                    .append(STARGAZERS)
                    .toString();

            this._forkHub = new StringBuffer(this._repoHub).append(SLASH)
                    .append(FORKS)
                    .toString();
        } else {

            /*
             * Initialize properties to non-null defaults as valid GitHub
             * repository data is not available.
             */

            this._landingBase = HTTP_HASH;
            this._landingURL = HTTP_HASH;
            this._slideshowURL = HTTP_HASH;
            this._orgHub = HTTP_HASH;
            this._repoHub = HTTP_HASH;
            this._starHub = HTTP_HASH;
            this._forkHub = HTTP_HASH;
        }
    }

    public static GitRepoRenderer build(PitchParams pp,
                                        GitRepoModel grm,
                                        Configuration cfg) {

        return new GitRepoRenderer(pp, grm, cfg);
    }

    /*
     * Return PitchParams on this GitRepoRenderer.
     */
    public PitchParams params() {
        return _pp;
    }

    /*
     * Return GitRepoModel on this GitRepoRenderer.
     */
    public GitRepoModel model() {
        return _grm;
    }

    /*
     * Return GitHub {user} name.
     */
    public String user() {
        return _pp.user;
    }

    /*
     * Return GitHub {repo} name.
     */
    public String repo() {
        return _pp.repo;
    }

    /*
     * Return GitHub {branch} name.
     */
    public String branch() {
        return _pp.branch;
    }

    /*
     * Return pitch slideshow theme.
     */
    public String theme() {
        return _pp.theme;
    }

    /*
     * Return true is branch is GIT_MASTER.
     */
    public boolean isMaster() {
        return _pp.isMaster();
    }

    /*
     * Return pitch slideshow theme.css.
     */
    public String themeCSS() {
        return _pp.theme + CSS;
    }

    /*
     * Return relative URL to landing view.
     */
    public String landingURL() {
        return _landingURL;
    }

    /*
     * Return relative URL to landing view.
     */
    public String landingURL(String theme) {
        return com.gitpitch.controllers.routes.PitchController.landing(_grm.owner(),
                _grm.name(),
                _pp.branch,
                theme,
                _pp.notes, null).toString();
    }

    /*
     * Return relative URL to slideshow view.
     */
    public String slideshowURL() {
        return _slideshowURL;
    }

    /*
     * Return relative URL to PITCHME.md markdown.
     */
    public String markdownURL() {
        return _markdownURL;
    }

    /*
     * Return https://github.com/{user}.
     */
    public String orgHub() {
        return _orgHub;
    }

    /*
     * Return https://github.com/{user}/{repo}.
     */
    public String repoHub() {
        return _repoHub;
    }

    /*
     * Return https://github.com/{user}/{repo}/stargazers.
     */
    public String starHub() {
        return _starHub;
    }

    /*
     * Return https://github.com/{user}/{repo}/network.
     */
    public String forkHub() {
        return _forkHub;
    }

    /*
     * Return number of GitHub repository stargazers.
     */
    public int stargazers() {
        return (_grm != null) ? _grm.stargazers() : 0;
    }

    /*
     * Return number of GitHub repository forks.
     */
    public int forks() {
        return (_grm != null) ? _grm.forks() : 0;
    }

    /*
     * Return GitHub repository language.
     */
    public String repoLang() {
        return (_grm != null) ? _grm.lang() : null;
    }

    /*
     * Return true if ViewModel represents a valid
     * repository on GitHub.
     */
    public boolean isValid() {
        return _grm != null;
    }

    /*
     * Return the "best fit" repo language or branch
     * name when rendering the landing page.
     */
    public String displayLangOrBranch() {

        if (!isValid()) {
            return _pp.branch;
        } else if (isMaster() && repoLang() != null) {
            return repoLang();
        } else {
            return _pp.branch;
        }
    }

    public String pageLink() {
        return pageLink(false);
    }

    public String pageLink(boolean absolute) {

        if (absolute)
            return com.gitpitch.controllers.routes.PitchController.landing(_pp.user,
                    _pp.repo,
                    _pp.branch,
                    _pp.theme,
                    _pp.notes, null)
                    .absoluteURL(isEncrypted(),
                            hostname());
        else
            return com.gitpitch.controllers.routes.PitchController.landing(_pp.user,
                    _pp.repo,
                    _pp.branch,
                    _pp.theme,
                    _pp.notes, null).toString();
    }

    public String pageLinkWithTheme(String theme) {

        return com.gitpitch.controllers.routes.PitchController.landing(_pp.user,
                _pp.repo,
                _pp.branch,
                theme,
                _pp.notes, null).toString();
    }

    public String printLink() {

        return com.gitpitch.controllers.routes.PitchController.print(_pp.user,
                _pp.repo,
                _pp.branch,
                _pp.theme,
                _pp.notes).toString();
    }

    public String offlineLink() {

        return com.gitpitch.controllers.routes.PitchController.offline(_pp.user,
                _pp.repo,
                _pp.branch,
                _pp.theme,
                _pp.notes).toString();
    }

    public String pageEmbed() {

        return new StringBuffer(EMBED_OPEN)
                .append(pageLink(true))
                .append(EMBED_CLOSE)
                .toString();
    }

    public String pageBadge() {

        return new StringBuffer(BADGE_OPEN)
                .append(pageLink(true))
                .append(BADGE_CLOSE)
                .toString();
    }

    /*
     * Return string representation of ViewModel.
     */
    public String toString() {
        return _landingURL;
    }

    private boolean isEncrypted() {
        return _cfg.getBoolean("gitpitch.https");
    }

    private String hostname() {
        return _cfg.getString("gitpitch.hostname");
    }

    private static final String GIT_MASTER = "master";
    private static final String CSS = ".css";
    private static final String SLIDESHOW = "/pitchme/slideshow/";
    private static final String MARKDOWN = "/pitchme/markdown/";
    private static final String SLASH = "/";
    private static final String QMARK_BRANCH = "?b=";
    private static final String QMARK_THEME = "&t=";
    private static final String GIT_COM = "https://github.com/";
    private static final String STARGAZERS = "stargazers";
    private static final String FORKS = "network";
    private static final String HTTP_HASH = "#";
    private static final String EMBED_OPEN =
            "<iframe width='770' height='515' src='";
    private static final String EMBED_CLOSE =
            "' frameborder='0' allowfullscreen></iframe>";
    private static final String BADGE_OPEN =
            "[![GitPitch](https://gitpitch.com/assets/badge.svg)](";
    private static final String BADGE_CLOSE = ")";
    private static final String DEFAULT_THEME = "white";
    private static final List<String> THEMES = Arrays.asList(DEFAULT_THEME,
            "beige", "black", "moon", "night", "sky", "white");
}
