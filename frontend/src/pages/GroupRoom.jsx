import { useNavigate, useParams } from "react-router-dom";

function GroupRoom() {
  const { id } = useParams();
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-slate-50 px-6 py-10">
      <div className="mx-auto max-w-5xl">
        <button
          onClick={() => navigate("/groups")}
          className="mb-6 rounded-lg border border-slate-200 bg-white px-4 py-2 text-sm font-medium text-slate-700 hover:bg-slate-100"
        >
          ← Back to Groups
        </button>

        <div className="rounded-2xl bg-white p-8 shadow-sm">
          <div className="mb-8">
            <p className="text-sm font-semibold uppercase tracking-wider text-indigo-600">
              Group Converge
            </p>

            <h1 className="mt-2 text-3xl font-bold text-slate-900">
              Group Room
            </h1>

            <p className="mt-2 text-slate-500">
              Collaborate with your group and find what everyone agrees with.
            </p>

            {id && (
              <p className="mt-3 text-sm text-slate-400">
                Group ID: {id}
              </p>
            )}
          </div>

          <div className="grid gap-6 md:grid-cols-3">
            <div className="rounded-xl border border-slate-200 p-5">
              <h2 className="font-semibold text-slate-900">Members</h2>
              <p className="mt-2 text-sm text-slate-500">
                Group members will appear here.
              </p>
            </div>

            <div className="rounded-xl border border-slate-200 p-5">
              <h2 className="font-semibold text-slate-900">Preferences</h2>
              <p className="mt-2 text-sm text-slate-500">
                Submit your entertainment preferences.
              </p>
            </div>

            <div className="rounded-xl border border-slate-200 p-5">
              <h2 className="font-semibold text-slate-900">Matching</h2>
              <p className="mt-2 text-sm text-slate-500">
                Matching recommendations will appear here.
              </p>
            </div>
          </div>

          <div className="mt-8 rounded-xl bg-indigo-50 p-6">
            <h2 className="text-lg font-semibold text-indigo-900">
              Find what everyone agrees with.
            </h2>

            <p className="mt-2 text-sm text-indigo-700">
              Complete your preferences to generate group recommendations.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default GroupRoom;