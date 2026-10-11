
import { useCallback, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";

const initialPreferences = {
  category: "Movie",
  genre: "",
  language: "",
  maxBudget: 500,
  maxDurationMinutes: 180,
  preferredStartTime: "18:00",
  maxDistanceKm: 20,
};

function GroupRoom() {
  const { groupCode } = useParams();
  const navigate = useNavigate();

  const [members, setMembers] = useState([]);
  const [membersLoading, setMembersLoading] = useState(true);
  const [membersError, setMembersError] = useState("");

  const [preferences, setPreferences] = useState(initialPreferences);
  const [preferencesLoading, setPreferencesLoading] = useState(true);
  const [preferencesError, setPreferencesError] = useState("");
  const [savingPreferences, setSavingPreferences] = useState(false);
  const [preferencesMessage, setPreferencesMessage] = useState("");

  const [matches, setMatches] = useState([]);
  const [matchesLoading, setMatchesLoading] = useState(false);
  const [matchesError, setMatchesError] = useState("");
  const [hasLoadedMatches, setHasLoadedMatches] = useState(false);

  const loadMembers = useCallback(async () => {
    if (!groupCode) return;

    setMembersLoading(true);
    setMembersError("");

    try {
      const response = await api.get(
        `/groups/${encodeURIComponent(groupCode)}/members`
      );
      setMembers(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      console.error("Failed to load members:", error);
      setMembersError(
        error.response?.data?.message || "Unable to load group members."
      );
    } finally {
      setMembersLoading(false);
    }
  }, [groupCode]);

  const loadPreferences = useCallback(async () => {
    if (!groupCode) return;

    setPreferencesLoading(true);
    setPreferencesError("");

    try {
      const response = await api.get(
        `/groups/${encodeURIComponent(groupCode)}/preferences/me`
      );

      const saved = response.data;

      if (saved) {
        setPreferences((current) => ({
          ...current,
          ...saved,
          maxBudget: saved.maxBudget ?? current.maxBudget,
          maxDurationMinutes:
            saved.maxDurationMinutes ?? current.maxDurationMinutes,
          maxDistanceKm: saved.maxDistanceKm ?? current.maxDistanceKm,
          preferredStartTime:
            saved.preferredStartTime?.slice(0, 5) ??
            current.preferredStartTime,
        }));
      }
    } catch (error) {
      // A member may not have saved preferences yet.
      if (error.response?.status !== 404) {
        console.error("Failed to load preferences:", error);
        setPreferencesError(
          error.response?.data?.message ||
            "Could not load saved preferences. You can still submit the form."
        );
      }
    } finally {
      setPreferencesLoading(false);
    }
  }, [groupCode]);

  useEffect(() => {
    loadMembers();
    loadPreferences();
  }, [loadMembers, loadPreferences]);

  function handlePreferenceChange(event) {
    const { name, value } = event.target;

    setPreferences((current) => ({
      ...current,
      [name]: value,
    }));

    setPreferencesMessage("");
  }

  async function handleSavePreferences(event) {
    event.preventDefault();

    setSavingPreferences(true);
    setPreferencesError("");
    setPreferencesMessage("");

    const payload = {
      category: preferences.category.trim(),
      genre: preferences.genre.trim(),
      language: preferences.language.trim(),
      maxBudget: Number(preferences.maxBudget),
      maxDurationMinutes: Number(preferences.maxDurationMinutes),
      preferredStartTime: preferences.preferredStartTime,
      maxDistanceKm: Number(preferences.maxDistanceKm),
    };

    try {
      await api.post(
        `/groups/${encodeURIComponent(groupCode)}/preferences`,
        payload
      );

      setPreferencesMessage("Preferences saved successfully!");
    } catch (error) {
      console.error("Failed to save preferences:", error);
      setPreferencesError(
        error.response?.data?.message ||
          "Could not save preferences. Check your entries and try again."
      );
    } finally {
      setSavingPreferences(false);
    }
  }

  async function loadMatches() {
    setMatchesLoading(true);
    setMatchesError("");
    setHasLoadedMatches(true);

    try {
      const response = await api.get(
        `/groups/${encodeURIComponent(groupCode)}/matches`
      );

      setMatches(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      console.error("Failed to load matches:", error);
      setMatchesError(
        error.response?.data?.message ||
          "Could not load matches. Make sure group members have submitted their preferences."
      );
      setMatches([]);
    } finally {
      setMatchesLoading(false);
    }
  }

  function formatTime(time) {
    if (!time) return "Time not specified";

    const [hours, minutes] = time.split(":").map(Number);
    const date = new Date();
    date.setHours(hours, minutes, 0, 0);

    return date.toLocaleTimeString([], {
      hour: "numeric",
      minute: "2-digit",
    });
  }

  function formatPrice(price) {
    if (price == null) return "Price unavailable";

    return `₹${Number(price).toLocaleString("en-IN")}`;
  }

  function formatScore(score) {
    return `${Number(score ?? 0).toFixed(1)}%`;
  }

  return (
    <main className="min-h-screen bg-slate-50 px-4 py-8 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-6xl">
        <button
          onClick={() => navigate("/groups")}
          className="mb-6 rounded-lg border border-slate-200 bg-white px-4 py-2 text-sm font-medium text-slate-700 hover:bg-slate-100"
        >
          ← Back to Groups
        </button>

        <header className="mb-8">
          <p className="text-sm font-semibold uppercase tracking-wider text-indigo-600">
            Group Converge
          </p>

          <h1 className="mt-2 text-3xl font-bold text-slate-900 sm:text-4xl">
            Group Room
          </h1>

          <p className="mt-2 text-slate-500">
            Share preferences, explore recommendations, and find what everyone
            agrees with.
          </p>

          {groupCode && (
            <p className="mt-3 text-sm text-slate-400">
              Group Code: {groupCode}
            </p>
          )}
        </header>

        <div className="grid gap-6 lg:grid-cols-3">
          {/* MEMBERS */}
          <section className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
            <div className="flex items-center justify-between gap-2">
              <h2 className="text-lg font-semibold text-slate-900">
                Members
              </h2>
              <span className="rounded-full bg-indigo-50 px-3 py-1 text-sm font-medium text-indigo-700">
                {members.length}
              </span>
            </div>

            {membersLoading ? (
              <p className="mt-4 text-sm text-slate-500">
                Loading members...
              </p>
            ) : membersError ? (
              <div className="mt-4">
                <p className="text-sm text-red-600">{membersError}</p>
                <button
                  onClick={loadMembers}
                  className="mt-2 text-sm font-medium text-indigo-600 hover:underline"
                >
                  Try again
                </button>
              </div>
            ) : members.length > 0 ? (
              <ul className="mt-4 space-y-3">
                {members.map((member, index) => (
                  <li
                    key={member.userId ?? member.email ?? index}
                    className="flex items-center gap-3 rounded-xl bg-slate-50 p-3"
                  >
                    <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gradient-to-br from-indigo-500 to-violet-600 font-semibold text-white">
                      {(member.fullName || member.email || "?")
                        .charAt(0)
                        .toUpperCase()}
                    </div>

                    <div className="min-w-0">
                      <p className="truncate font-medium text-slate-800">
                        {member.fullName ||
                          member.email ||
                          "Unnamed member"}
                      </p>
                      {member.role && (
                        <p className="text-xs capitalize text-slate-500">
                          {member.role.toLowerCase()}
                        </p>
                      )}
                    </div>
                  </li>
                ))}
              </ul>
            ) : (
              <p className="mt-4 text-sm text-slate-500">
                No members found in this group.
              </p>
            )}
          </section>

          {/* PREFERENCES */}
          <section className="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm lg:col-span-2">
            <h2 className="text-lg font-semibold text-slate-900">
              Your Preferences
            </h2>

            <p className="mt-1 text-sm text-slate-500">
              Tell us what works best for you.
            </p>

            {preferencesLoading ? (
              <p className="mt-4 text-sm text-slate-500">
                Loading your preferences...
              </p>
            ) : (
              <form
                onSubmit={handleSavePreferences}
                className="mt-5 space-y-4"
              >
                <div className="grid gap-4 sm:grid-cols-2">
                  <div>
                    <label
                      htmlFor="category"
                      className="mb-1 block text-sm font-medium text-slate-700"
                    >
                      Category
                    </label>
                    <select
                      id="category"
                      name="category"
                      value={preferences.category}
                      onChange={handlePreferenceChange}
                      required
                      maxLength={30}
                      className="w-full rounded-lg border border-slate-200 bg-white px-3 py-2.5 text-slate-900"
                    >
                      <option value="Movie">Movie</option>
                      <option value="Dining">Dining</option>
                      <option value="Activity">Activity</option>
                      <option value="Event">Event</option>
                      <option value="Entertainment">Entertainment</option>
                    </select>
                  </div>

                  <div>
                    <label
                      htmlFor="genre"
                      className="mb-1 block text-sm font-medium text-slate-700"
                    >
                      Genre
                    </label>
                    <input
                      id="genre"
                      name="genre"
                      value={preferences.genre}
                      onChange={handlePreferenceChange}
                      required
                      maxLength={50}
                      placeholder="e.g. Action, Comedy"
                      className="w-full rounded-lg border border-slate-200 bg-white px-3 py-2.5 text-slate-900"
                    />
                  </div>

                  <div>
                    <label
                      htmlFor="language"
                      className="mb-1 block text-sm font-medium text-slate-700"
                    >
                      Language
                    </label>
                    <input
                      id="language"
                      name="language"
                      value={preferences.language}
                      onChange={handlePreferenceChange}
                      required
                      maxLength={50}
                      placeholder="e.g. English, Tamil"
                      className="w-full rounded-lg border border-slate-200 bg-white px-3 py-2.5 text-slate-900"
                    />
                  </div>

                  <div>
                    <label
                      htmlFor="maxBudget"
                      className="mb-1 block text-sm font-medium text-slate-700"
                    >
                      Maximum budget (₹)
                    </label>
                    <input
                      id="maxBudget"
                      name="maxBudget"
                      type="number"
                      min="1"
                      step="1"
                      value={preferences.maxBudget}
                      onChange={handlePreferenceChange}
                      required
                      className="w-full rounded-lg border border-slate-200 bg-white px-3 py-2.5 text-slate-900"
                    />
                  </div>

                  <div>
                    <label
                      htmlFor="maxDurationMinutes"
                      className="mb-1 block text-sm font-medium text-slate-700"
                    >
                      Maximum duration (minutes)
                    </label>
                    <input
                      id="maxDurationMinutes"
                      name="maxDurationMinutes"
                      type="number"
                      min="1"
                      value={preferences.maxDurationMinutes}
                      onChange={handlePreferenceChange}
                      required
                      className="w-full rounded-lg border border-slate-200 bg-white px-3 py-2.5 text-slate-900"
                    />
                  </div>

                  <div>
                    <label
                      htmlFor="preferredStartTime"
                      className="mb-1 block text-sm font-medium text-slate-700"
                    >
                      Preferred start time
                    </label>
                    <input
                      id="preferredStartTime"
                      name="preferredStartTime"
                      type="time"
                      value={preferences.preferredStartTime}
                      onChange={handlePreferenceChange}
                      required
                      className="w-full rounded-lg border border-slate-200 bg-white px-3 py-2.5 text-slate-900"
                    />
                  </div>

                  <div className="sm:col-span-2">
                    <label
                      htmlFor="maxDistanceKm"
                      className="mb-1 block text-sm font-medium text-slate-700"
                    >
                      Maximum distance (km)
                    </label>
                    <input
                      id="maxDistanceKm"
                      name="maxDistanceKm"
                      type="number"
                      min="1"
                      value={preferences.maxDistanceKm}
                      onChange={handlePreferenceChange}
                      required
                      className="w-full rounded-lg border border-slate-200 bg-white px-3 py-2.5 text-slate-900 sm:max-w-xs"
                    />
                  </div>
                </div>

                {preferencesError && (
                  <p role="alert" className="text-sm text-red-600">
                    {preferencesError}
                  </p>
                )}

                {preferencesMessage && (
                  <p role="status" className="text-sm text-green-700">
                    {preferencesMessage}
                  </p>
                )}

                <button
                  type="submit"
                  disabled={savingPreferences}
                  className="rounded-lg bg-indigo-600 px-5 py-3 font-semibold text-white transition hover:bg-indigo-700 disabled:cursor-not-allowed disabled:opacity-60"
                >
                  {savingPreferences ? "Saving..." : "Save Preferences"}
                </button>
              </form>
            )}
          </section>
        </div>

        {/* MATCHING */}
        <section className="mt-6 rounded-2xl border border-slate-200 bg-white p-5 shadow-sm sm:p-6">
          <div className="flex flex-col justify-between gap-4 sm:flex-row sm:items-center">
            <div>
              <p className="text-sm font-semibold uppercase tracking-wider text-indigo-600">
                Group Converge
              </p>
              <h2 className="mt-1 text-2xl font-bold text-slate-900">
                Matching Recommendations
              </h2>
              <p className="mt-1 text-sm text-slate-500">
                Discover experiences scored against your group's preferences.
              </p>
            </div>

            <button
              onClick={loadMatches}
              disabled={matchesLoading}
              className="shrink-0 rounded-lg bg-indigo-600 px-5 py-3 font-semibold text-white transition hover:bg-indigo-700 disabled:cursor-not-allowed disabled:opacity-60"
            >
              {matchesLoading
                ? "Finding matches..."
                : "Find Group Matches"}
            </button>
          </div>

          {matchesError && (
            <p role="alert" className="mt-5 text-sm text-red-600">
              {matchesError}
            </p>
          )}

          {matchesLoading && (
            <p className="mt-5 text-sm text-slate-500">
              Finding experiences for your group...
            </p>
          )}

          {!matchesLoading &&
            hasLoadedMatches &&
            !matchesError &&
            matches.length === 0 && (
              <p className="mt-5 rounded-xl bg-slate-50 p-4 text-sm text-slate-600">
                No matches found yet. Ask group members to save their
                preferences, then try again.
              </p>
            )}

          {!hasLoadedMatches && (
            <p className="mt-5 rounded-xl bg-slate-50 p-4 text-sm text-slate-600">
              Save your preferences, make sure your group members have
              submitted theirs, and click Find Group Matches.
            </p>
          )}

          {!matchesLoading && matches.length > 0 && (
            <div className="mt-6 grid gap-5 md:grid-cols-2 xl:grid-cols-3">
              {matches.map((match, index) => (
                <article
                  key={`${match.experienceId ?? "experience"}-${match.scheduleId ?? index}`}
                  className="overflow-hidden rounded-2xl border border-slate-200 bg-white transition hover:-translate-y-1 hover:shadow-lg"
                >
                  <div className="bg-gradient-to-br from-slate-900 via-indigo-950 to-violet-900 p-5 text-white">
                    <div className="flex items-start justify-between gap-3">
                      <div>
                        <p className="text-xs font-semibold uppercase tracking-wider text-indigo-200">
                          {match.category || "Experience"}
                        </p>
                        <h3 className="mt-2 text-xl font-bold">
                          {match.title || "Recommended experience"}
                        </h3>
                      </div>

                      <div className="shrink-0 rounded-xl border border-white/20 bg-white/10 px-3 py-2 text-center">
                        <p className="text-xl font-bold">
                          {formatScore(match.groupScore)}
                        </p>
                        <p className="text-xs text-indigo-100">Group score</p>
                      </div>
                    </div>
                  </div>

                  <div className="space-y-4 p-5">
                    <div className="flex flex-wrap gap-2 text-xs">
                      {match.genre && (
                        <span className="rounded-full bg-indigo-50 px-3 py-1 text-indigo-700">
                          {match.genre}
                        </span>
                      )}
                      {match.language && (
                        <span className="rounded-full bg-slate-100 px-3 py-1 text-slate-700">
                          {match.language}
                        </span>
                      )}
                      {match.durationMinutes != null && (
                        <span className="rounded-full bg-slate-100 px-3 py-1 text-slate-700">
                          {match.durationMinutes} min
                        </span>
                      )}
                    </div>

                    <div className="space-y-2 text-sm text-slate-600">
                      <p>
                        <span className="font-medium text-slate-800">
                          Price:
                        </span>{" "}
                        {formatPrice(match.startingPrice)}
                      </p>

                      {(match.venueName || match.city) && (
                        <p>
                          <span className="font-medium text-slate-800">
                            Location:
                          </span>{" "}
                          {[match.venueName, match.city]
                            .filter(Boolean)
                            .join(", ")}
                        </p>
                      )}

                      {match.scheduleDate && (
                        <p>
                          <span className="font-medium text-slate-800">
                            Date:
                          </span>{" "}
                          {match.scheduleDate}
                        </p>
                      )}

                      {match.startTime && (
                        <p>
                          <span className="font-medium text-slate-800">
                            Starts:
                          </span>{" "}
                          {formatTime(match.startTime)}
                          {match.endTime
                            ? ` – ${formatTime(match.endTime)}`
                            : ""}
                        </p>
                      )}
                    </div>

                    <div className="grid grid-cols-2 gap-2">
                      <div className="rounded-lg bg-slate-50 p-3">
                        <p className="text-xs text-slate-500">
                          Average member score
                        </p>
                        <p className="mt-1 font-semibold text-slate-900">
                          {formatScore(match.averageMemberScore)}
                        </p>
                      </div>

                      <div className="rounded-lg bg-slate-50 p-3">
                        <p className="text-xs text-slate-500">
                          Lowest member score
                        </p>
                        <p className="mt-1 font-semibold text-slate-900">
                          {formatScore(match.minimumMemberScore)}
                        </p>
                      </div>
                    </div>

                    {Array.isArray(match.reasons) &&
                      match.reasons.length > 0 && (
                        <div>
                          <h4 className="text-sm font-semibold text-slate-800">
                            Why it matches
                          </h4>
                          <ul className="mt-2 space-y-1">
                            {match.reasons.map((reason, reasonIndex) => (
                              <li
                                key={reasonIndex}
                                className="text-sm text-slate-600"
                              >
                                <span className="mr-2 text-green-600">✓</span>
                                {reason}
                              </li>
                            ))}
                          </ul>
                        </div>
                      )}

                    {Array.isArray(match.compromises) &&
                      match.compromises.length > 0 && (
                        <div>
                          <h4 className="text-sm font-semibold text-slate-800">
                            Possible compromises
                          </h4>
                          <ul className="mt-2 space-y-1">
                            {match.compromises.map((compromise, index) => (
                              <li
                                key={index}
                                className="text-sm text-slate-600"
                              >
                                <span className="mr-2 text-amber-500">•</span>
                                {compromise}
                              </li>
                            ))}
                          </ul>
                        </div>
                      )}

                    <button
                      type="button"
                      onClick={() =>
                        navigate(`/experience/${match.experienceId}`)
                      }
                      disabled={!match.experienceId}
                      className="w-full rounded-lg border border-indigo-200 px-4 py-2.5 font-semibold text-indigo-700 transition hover:bg-indigo-50 disabled:cursor-not-allowed disabled:opacity-50"
                    >
                      View Experience
                    </button>
                  </div>
                </article>
              ))}
            </div>
          )}
        </section>

        <div className="mt-6 rounded-2xl bg-indigo-50 p-6">
          <h2 className="text-lg font-semibold text-indigo-950">
            Find what everyone agrees with.
          </h2>
          <p className="mt-2 text-sm text-indigo-800">
            Every recommendation considers the group's preferences and
            highlights where members agree or may need to compromise.
          </p>
        </div>
      </div>
    </main>
  );
}

export default GroupRoom;
